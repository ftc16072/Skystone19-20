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
    public DistanceSensor downdistance;
    private static final double DOWN_DISTANCE_CM = 5.5;
    private static final double UP_DISTANCE_CM = 58;

    /**
     * This initializes our Lifter.
     * it makes sure that it will break at 0
     *
     * @param hwmap This is the Hardware map from the configuration
     */
    void init(HardwareMap hwmap) {
        lift = hwmap.get(DcMotor.class, "lifter");
        downdistance = hwmap.get(DistanceSensor.class, "downward_distance");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
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
                new QQ_TestMotor("lift-Up", 0.2, lift),
                new QQ_TestDistanceSensor("Down Sensor:", downdistance)
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
            if (downdistance.getDistance(DistanceUnit.CM) <= DOWN_DISTANCE_CM) {
                speed = 0;
                returnValue = false;
            }
        } else {
            if (downdistance.getDistance(DistanceUnit.CM) >= UP_DISTANCE_CM) {
                speed = 0;
                returnValue = false;
            }
        }
        lift.setPower(speed);
        return returnValue;
    }

    /**
     * gets encoder ticks of lift motor
     *
     * @return returns encoder position of the lift
     */
    public int getPosition() {
        return lift.getCurrentPosition();
    }
}
