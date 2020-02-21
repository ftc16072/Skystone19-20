package org.firstinspires.ftc.teamcode.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Arrays;
import java.util.List;

public class Rotator {
    private Servo rotator;
    private static final double LEFT_POSITION = 0.27;
    private static final double RIGHT_POSITION = 1;
    private static final double FIT_LOCATION = -80;



    /**
     * Initializes the rotator
     *
     * @param hwmap hardware map from the config
     */
    void init(HardwareMap hwmap) {
        rotator = hwmap.get(Servo.class, "rotator");
        rotator.scaleRange(LEFT_POSITION, RIGHT_POSITION);
    }

    /**
     * @return test that tests the rotator
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("rotator", 0, 1, rotator)
        );
    }

    /**
     * rotate the rotator based on a angle
     *
     * @param angle     what angle to rotate to (-PI/2) < angle < (PI/2) -- -90 < speed > 90
     * @param angleUnit what unit is the angle in (Radians, Degrees)
     * @param telemetry allows for printing what it is being set to
     */
    public void rotate(double angle, AngleUnit angleUnit, Telemetry telemetry) {
        double angleRadians = angleUnit.toRadians(angle);

        if (angleRadians < -(Math.PI / 2) || angleRadians > (Math.PI / 2)) { //throw away the bottom half of the joystick so it wont randomly spin around when its slightly bumped
            return;
        }

        double position = -1 * ((angleRadians / Math.PI) - 0.5);
        telemetry.addData("SetPosition", position);
        rotator.setPosition(position);


    }

    /**
     * rotates the rotator to the fit location
     *
     * @param telemetry allows us to send information back to the phone.
     */
    public void fit(Telemetry telemetry) {
        double angleRadians = AngleUnit.DEGREES.toRadians(FIT_LOCATION);
        double position = -1 * ((angleRadians / Math.PI) - 0.5);
        telemetry.addData("SetPosition", position);
        rotator.setPosition(position);
    }


}
