package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.util.Polar;
import org.firstinspires.ftc.teamcode.util.RobotPosition;


public class Navigation {
    private static final double DISTANCE_TOLERANCE = 2;
    private static final double ANGLE_TOLERANCE = AngleUnit.RADIANS.fromDegrees(1);
    private static final double KP_DISTANCE = 0.03;
    private static final double KP_ANGLE = 1;
    private static final double SLOWEST_SPEED = 0.2;
    public final MecanumDrive mecanumDrive = new MecanumDrive();
    private static BNO055IMU imu;
    private RobotPosition lastSetPosition;
    private static double imuOffset = 0;

    private static boolean redAlliance = false;

    /**
     * this initializes the imu and the mecanum drive and sets our position at (0,0) (center of the field)
     *
     * @param hwMap hardware map from configuration
     */
    void init(HardwareMap hwMap) {
        //   if (imu == null) {
            initializeImu(hwMap, 0);
        //}
        mecanumDrive.init(hwMap);
        setPosition(0, 0, DistanceUnit.CM);
    }

    /**
     * Initialized the Imu with the hardware map and the offset.
     *
     * @param hwMap hardware map used from the configuration
     * @param offset offset for the imu
     */
    public void initializeImu(HardwareMap hwMap, double offset) {
        imu = hwMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.calibrationDataFile = "BNO055IMUCalibration.json";
        imu.initialize(params);
        imuOffset = offset;
    }

    /**
     * Gives the robot the information it needs to make the controls field relative.
     *
     * @param angRadians angle of the robot in radians
     * @return returns the angle of the robot in radians
     */
    private double fromDriverRelToFieldRel(double angRadians) {
        if (redAlliance) {
            angRadians += Math.PI;
        }
        return angRadians;
    }

    /**
     * gets our imu heading
     *
     * @param angleUnit this determines the angle unit (degrees/radians) that it will return in
     * @return returns the current angle with the offset in the angleUnit specified
     */
    private double getHeading(AngleUnit angleUnit) {
        Orientation angles;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);

        return angleUnit.fromRadians(angles.firstAngle + imuOffset);
    }

    /**
     * this makes the driving field relative
     *
     * @param x      x Speed
     * @param y      y Speed
     * @param rotate rotate speed
     */
    public void driveFieldRelative(double x, double y, double rotate) {
        Polar drive = Polar.fromCartesian(x, y);
        double heading = getHeading(AngleUnit.RADIANS);
        heading = fromDriverRelToFieldRel(heading);
        drive.subtractAngle(heading);

        mecanumDrive.driveMecanum(drive.getY(), drive.getX(), rotate);
    }

    /**
     * this allows us to easily strafe
     *
     * @param speed this is the speed at which to strafe
     */
    public void strafe(double speed) {
        mecanumDrive.driveMecanum(0, speed, 0);
    }

    /**
     * this rotates from the field angle system to the robot angle system it then decides which way is faster to turn, and turns that way
     *
     * @param x     x speed
     * @param y     y speed
     * @param angle desired angle to turn to
     */
    public void driveFieldRelativeAngle(double x, double y, double angle) {
        angle = fromDriverRelToFieldRel(angle);

        double angle_in = angle - Math.PI / 2;  // convert to robot coordinates

        double delta = AngleUnit.normalizeRadians(getHeading(AngleUnit.RADIANS) - angle_in);

        double MAX_ROTATE = 0.7; //This is to shrink how fast we can rotate so we don't fly past the angle
        delta = Range.clip(delta, -MAX_ROTATE, MAX_ROTATE);
        driveFieldRelative(x, y, delta);
    }

    /**
     * this sets the mecanum drive max Speed
     *
     * @param speed this is the new max speed
     */
    public void setMecanumDriveMaxSpeed(double speed) {
        mecanumDrive.setMaxSpeed(speed);
    }

    /**
     * gets current max speed each wheel can turn at
     * @return mecanum drive's currently set max speed
     */
    public double getMecanumDriveMaxSpeed() {
        return mecanumDrive.getMaxSpeed();
    }

    /**
     * gets the robots estimated position by translating robot relative translation into field relative coordinates
     *
     * @return returns a RobotPosition that is the x, y, and angle that the robot moved
     * @see RobotPosition
     */
    public RobotPosition getEstimatedPosition() {
        double[] distanceDriven = mecanumDrive.getDistanceCm();

        Polar translation = Polar.fromCartesian(distanceDriven[0], -distanceDriven[1]);
        double rotate = getHeading(AngleUnit.RADIANS);
        translation.subtractAngle(-rotate); // transmogrify into field relative

        double estX = lastSetPosition.getX(DistanceUnit.CM) + translation.getX();
        double estY = lastSetPosition.getY(DistanceUnit.CM) + translation.getY();

        return new RobotPosition(estX, estY, DistanceUnit.CM, rotate, AngleUnit.RADIANS);
    }

    /**
     * sets the robot's estimated position
     *
     * @param x            believed x position
     * @param y            believed y position
     * @param distanceUnit what distanceUnit did you use
     */
    public void setPosition(double x, double y, DistanceUnit distanceUnit) {
        lastSetPosition = new RobotPosition(x, y, distanceUnit, getHeading(AngleUnit.RADIANS), AngleUnit.RADIANS);
        mecanumDrive.setEncoderOffsets();
    }

    /**
     * rotates to the angle specified
     *
     * @param angle     angle to rotate to
     * @param angleUnit angle unit that was used for the angle
     * @return returns true when the angle is reached
     */
    public boolean rotateTo(double angle, AngleUnit angleUnit) {
        double rotateSpeed;
        double MIN_TURN_SPEED = 0.1;

        double rotateDiff = AngleUnit.normalizeRadians(getHeading(AngleUnit.RADIANS) - angleUnit.toRadians(angle));
        if (Math.abs(rotateDiff) <= ANGLE_TOLERANCE) {
            mecanumDrive.driveMecanum(0, 0, 0);
            return true;
        } else {
            rotateSpeed = KP_ANGLE * rotateDiff;
            if (Math.abs(rotateSpeed) < MIN_TURN_SPEED) {
                rotateSpeed = MIN_TURN_SPEED * Math.signum(rotateSpeed);
            }
        }
        mecanumDrive.driveMecanum(0, 0, rotateSpeed);
        return false;
    }

    /**
     * drives to a specified position
     *
     * @param x            desired x position
     * @param y            desired y position
     * @param distanceUnit what distance unit was used in the coordinates
     * @return returns true when it reaches the desired location
     */
    public boolean driveTo(double x, double y, DistanceUnit distanceUnit) {
        RobotPosition estimatedPosition = getEstimatedPosition();
        double xDiff = distanceUnit.toCm(x) - estimatedPosition.getX(DistanceUnit.CM);
        double yDiff = distanceUnit.toCm(y) - estimatedPosition.getY(DistanceUnit.CM);

        double xSpeed = 0.0;
        double ySpeed = 0.0;

        if ((Math.abs(xDiff) <= DISTANCE_TOLERANCE) &&
                (Math.abs(yDiff) <= DISTANCE_TOLERANCE)) {
            mecanumDrive.driveMecanum(0, 0, 0);
            setPosition(estimatedPosition.getX(DistanceUnit.CM),
                    estimatedPosition.getY(DistanceUnit.CM),
                    DistanceUnit.CM);
            return true;
        }
        if (Math.abs(xDiff) > DISTANCE_TOLERANCE) { // if off on x move x
            xSpeed = Math.signum(xDiff) * Math.max(SLOWEST_SPEED, Math.abs(KP_DISTANCE * xDiff));
        }
        if (Math.abs(yDiff) > DISTANCE_TOLERANCE) { // if off on y move y
            ySpeed = Math.signum(yDiff) * Math.max(SLOWEST_SPEED, Math.abs(KP_DISTANCE * yDiff));
        }
        Polar drive = Polar.fromCartesian(xSpeed, ySpeed);
        drive.subtractAngle(-Math.PI / 2); //rotates it from field angles to robot angles

        driveFieldRelative(drive.getX(), drive.getY(), 0.0);
        return false;
    }

    /**
     * offsets the imu so it acts reset
     *
     * @param heading   heading to reset it to
     * @param angleUnit what angleUnit is the heading in
     */
    public void resetIMU(double heading, AngleUnit angleUnit) {
        double supposedHeading = angleUnit.toRadians(heading);
        double currentHeading = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle; //Because if we used our read imu method it would double the offset
        imuOffset = supposedHeading - currentHeading;
    }

    public boolean isRedAlliance() {
        return redAlliance;
    }

    public void setRedAlliance(boolean redAlliance) {
        Navigation.redAlliance = redAlliance;
    }
}