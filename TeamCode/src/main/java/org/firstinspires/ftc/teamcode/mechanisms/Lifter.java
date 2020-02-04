package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.opencv.core.Mat;

import java.util.Arrays;
import java.util.List;


public class Lifter {
    private DcMotor lift;
    public static double START_HEIGHT_CM = 1.0;
    private static double BRICK_HEIGHT_CM = DistanceUnit.INCH.toCm(4);
    private final static double GEAR_RATIO = 0.5;

    private static final double DOWN_DISTANCE_CM = 5.5;
    private static final double UP_DISTANCE_CM = 58;
    private static final double LIFT_SPOOL_CIRC_CM = 4.8 * Math.PI;
    private static final double TICKS_PER_MOTOR_REVOLUTION = 145.6;
    private static final double CM_PER_TICK = (LIFT_SPOOL_CIRC_CM * GEAR_RATIO) / (TICKS_PER_MOTOR_REVOLUTION);
    private double stoneDistanceCM;
    public double DISTANCE_SENSOR_TOLERANCE = 8;
    private double CM_HEIGHT_TOLERANCE = 1;
    public static double LiftingSpeedToHitBlockTop = 0.7;
    public static double KP_CM = 0.05;
    double desiredLocation = 0;

    private static final double DISTANCE_KP = 0.04;

    /**
     * This initializes our Lifter.
     * it makes sure that it will break at 0
     *
     * @param hwmap This is the Hardware map from the configuration
     */
    void init(HardwareMap hwmap) {
        lift = hwmap.get(DcMotor.class, "lifter");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * This gives us lifter tests for test wiring
     *
     * @return a lifter up and lifter down test for test wiring also a test for the downward_distance
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestMotor("Lift-Down", -0.2, lift),
                new QQ_TestMotor("lift-Up", 0.2, lift)
        );
    }

    /**
     * this lets us move our lift
     *
     * @param speed this is the speed to move the lift + is up and - is down
     * @return returns true if the move completed, and returns false if it stopped due to the distance sensor
     */
    public boolean move(double speed) {
        boolean returnValue = true;
        if (speed < 0) {
            if (getPosition(DistanceUnit.CM) <= 0) {
                speed = 0;
                returnValue = false;
            }
        } else {
            if (false) { // TODO: Find Encoder Top Limit
                speed = 0;
                returnValue = false;
            }
        }
        lift.setPower(speed);
        return returnValue;
    }

    public boolean moveToCM(double cm){
        double diff = cm - getCMLocation();
        if(Math.abs(diff) <= CM_HEIGHT_TOLERANCE){
            move(0);
            return true;
        }
        move(diff * KP_CM);
        return false;
    }

    public boolean moveByCm(double cm){
        if (desiredLocation == -1){
            desiredLocation = getCMLocation() + cm;
        }
        boolean movement = moveToCM(desiredLocation);
        if (movement){
            desiredLocation = -1;
            return true;
        }
        return false;
    }

    public double getCMLocation(){
        return getPosition(DistanceUnit.CM);
    }

    /**
     * gets encoder ticks of lift motor
     *
     * @return returns encoder position of the lift
     */
    public int getEncoderPosition() {
        return lift.getCurrentPosition();
    }

    /**
     * gets position of lift in requested distance unit
     *
     * @param distanceUnit what distance unit you want
     * @return returns lift position in requested distance unit
     */
    public double getPosition(DistanceUnit distanceUnit) {
        return distanceUnit.fromCm(getEncoderPosition() * CM_PER_TICK);
    }

    /**
     * moves lift to desired position
     *
     * @param position     what position to move lift to
     * @param distanceUnit what distance unit is the position in
     * @return whether it is at the position requested
     */
    public boolean goToPosition(double position, DistanceUnit distanceUnit) {
        double desiredPositionCM = distanceUnit.toCm(position);
        double diff = desiredPositionCM - getPosition(DistanceUnit.CM);
        if (Math.abs(diff) <= CM_HEIGHT_TOLERANCE) {
            move(0.0);
            return true;
        }

        move(diff * KP_CM);
        return false;

    }
    private double getTargetPosition(int numBricks){
        return START_HEIGHT_CM + (numBricks * BRICK_HEIGHT_CM);
    }

    public boolean liftToPlacing(){
        return false;
    }
}
