package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Arrays;
import java.util.List;

import static org.firstinspires.ftc.robotcore.internal.android.dx.rop.code.RegOps.OR;


class Rotator {
    private Servo rotator;

    void init(HardwareMap hwmap) {
        rotator = hwmap.get(Servo.class, "rotator");
    }

    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("rotator", 0, 1, rotator)
        );
    }

    void rotate(double angle, AngleUnit angleUnit, Telemetry telemetry){
        double angleRadians = angleUnit.toRadians(angle);

        if (angleRadians < -(Math.PI/2) || angleRadians > (Math.PI/2)){ //throw away the bottom half of the joystick so it wont randomly spin around when its slightly bumped
            return;
        }
        double position = -1 * ((angleRadians / Math.PI) - 0.5);      //+ 0.5;
        telemetry.addData("SetPosition", position);
        rotator.setPosition(position);


    }


}
