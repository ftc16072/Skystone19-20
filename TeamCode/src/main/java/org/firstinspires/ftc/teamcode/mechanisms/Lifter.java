package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Arrays;
import java.util.List;


public class Lifter {
    private DcMotor lift;
    public DistanceSensor middleSensor;
    public static double START_HEIGHT_CM = 1.0;
    private static double BRICK_HEIGHT_CM = DistanceUnit.INCH.toCm(4);
    private static final double DOWN_DISTANCE_CM = 5.5;
    private static final double UP_DISTANCE_CM = 58;
    private static final double LIFT_SPOOL_CIRC_CM = 4.8 * Math.PI;
    private static final double TICKS_PER_MOTOR_REVOLUTION = 145.6;
    private static final double GEAR_RATIO = 0.5;
    private static final double CM_PER_TICK = LIFT_SPOOL_CIRC_CM / (TICKS_PER_MOTOR_REVOLUTION * GEAR_RATIO);
    private double stoneDistanceCM;
    private double DISTANCE_SENSOR_TOLERANCE = 8;
    private double CM_HEIGHT_TOLERANCE = 1;
    public static double LiftingSpeedToHitBlockTop = 0.7;
    public static double KP_CM = 1;
    double desiredLocation = -1;

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
            if (getPosition() <= 0) {
                speed = 0;
                returnValue = false;
            }
        } else {
            if (true) { // TODO: Find Encoder Top Limit
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
        return getPosition() * CM_PER_TICK;
    }

    /**
     * gets encoder ticks of lift motor
     * @return returns encoder position of the lift
     */
    public int getPosition() {
        return lift.getCurrentPosition();
    }
    private double getTargetPosition(int numBricks){
        return START_HEIGHT_CM + (numBricks * BRICK_HEIGHT_CM);
    }

    public boolean liftToPlacing(){
        if(stoneDistanceCM == 0){
            stoneDistanceCM = middleSensor.getDistance(DistanceUnit.CM);
        }
        if(middleSensor.getDistance(DistanceUnit.CM) <= (stoneDistanceCM + DISTANCE_SENSOR_TOLERANCE)){
            move(LiftingSpeedToHitBlockTop);
        } else {
            if(desiredLocation == 0) {
                int numBricks = 0;
                while (getTargetPosition(numBricks) < getCMLocation()) {
                    numBricks++;
                }
                desiredLocation = getTargetPosition(numBricks);
            }
            boolean atHeight = moveToCM(desiredLocation);
            if(atHeight){
                stoneDistanceCM = 0;
                desiredLocation = 0;
                return true;
            }
        }
        return false;
    }
}
