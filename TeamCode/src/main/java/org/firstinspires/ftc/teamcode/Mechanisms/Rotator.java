package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Arrays;
import java.util.List;


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

    void rotate(double angle, AngleUnit angleUnit){
        double angleRadians = angleUnit.toRadians(angle);
        double position = angleRadians / Math.PI;
        rotator.setPosition(position);


    }


}
