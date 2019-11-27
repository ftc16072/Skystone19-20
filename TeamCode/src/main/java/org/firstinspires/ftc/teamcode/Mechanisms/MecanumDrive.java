package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.GeneralMatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;

import java.util.Arrays;
import java.util.List;

class MecanumDrive {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;

    public static double GEAR_RATIO = 0.5;
    public static double WHEEL_RADIUS = 5.0;  // 5 cm
    public static double TICKS_PER_ROTATION = 383.6;

    public static double CM_PER_TICK = (2 * Math.PI * WHEEL_RADIUS) / TICKS_PER_ROTATION;

    private double maxSpeed = 1.0;

    private MatrixF conversion;
    private GeneralMatrixF encoderMatrix = new GeneralMatrixF(3,1);

    MecanumDrive(){
        float[] data = {1.0f, 1.0f, 1.0f,
                        1.0f, -1.0f, -1.0f,
                       1.0f, -1.0f, 1.0f};
        conversion = new GeneralMatrixF(3, 3, data);
        conversion = conversion.inverted();
    }

    void init(HardwareMap hwMap) {
        frontLeft = hwMap.get(DcMotor.class, "front_left");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight = hwMap.get(DcMotor.class, "front_right");
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft = hwMap.get(DcMotor.class, "back_left");
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight = hwMap.get(DcMotor.class, "back_right");
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestMotor("Mecanum Wheel -> Front Left", 0.3, frontLeft),
                new QQ_TestMotor("Mecanum Wheel -> Front Right", 0.3, frontRight),
                new QQ_TestMotor("Mecanum Wheel -> Back Left", 0.3, backLeft),
                new QQ_TestMotor("Mecanum Wheel -> Back Right", 0.3, backRight));
     }

    private void setSpeeds(double flSpeed, double frSpeed, double blSpeed, double brSpeed) {
        double largest = maxSpeed;
        largest = Math.max(largest, Math.abs(flSpeed));
        largest = Math.max(largest, Math.abs(frSpeed));
        largest = Math.max(largest, Math.abs(blSpeed));
        largest = Math.max(largest, Math.abs(brSpeed));

        frontLeft.setPower(flSpeed / largest);
        frontRight.setPower(frSpeed / largest);
        backLeft.setPower(blSpeed / largest);
        backRight.setPower(brSpeed / largest);
    }

    void driveMecanum(double forward, double strafe, double rotate) {
        double frontLeftSpeed = forward + strafe + rotate;
        double frontRightSpeed = forward - strafe - rotate;
        double backLeftSpeed = forward - strafe + rotate;
        double backRightSpeed = forward + strafe - rotate;

        setSpeeds(frontLeftSpeed, frontRightSpeed, backLeftSpeed, backRightSpeed);
    }
    double[] getDistenceCm(){
        double[] distances = {0.0, 0.0};

        encoderMatrix.put(0, 0,(float) (frontLeft.getCurrentPosition() * CM_PER_TICK));
        encoderMatrix.put(1, 0, (float) (frontRight.getCurrentPosition() * CM_PER_TICK));
        encoderMatrix.put(2, 0, (float) (backLeft.getCurrentPosition() * CM_PER_TICK));

        MatrixF distanceMatrix = conversion.multiplied(encoderMatrix);
        distances[0] = distanceMatrix.get(0, 0);
        distances[1] = distanceMatrix.get(1, 0);

        return distances;
    }

    void reportEncoders(Telemetry telemetry) {
        telemetry.addData("Encoders", "%d %d %d %d",
                frontLeft.getCurrentPosition(),
                frontRight.getCurrentPosition(),
                backLeft.getCurrentPosition(),
                backRight.getCurrentPosition());


    }
    void setMaxSpeed(double speed){
        maxSpeed = Math.min(speed, 1.0);
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
