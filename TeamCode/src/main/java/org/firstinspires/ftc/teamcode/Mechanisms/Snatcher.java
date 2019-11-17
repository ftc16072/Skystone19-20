package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;


class Snatcher {
    private double LOWERED_POSITION = 0.6;
    private double RAISED_POSITION = 1.0;
    private Servo snatcher;

    void init(HardwareMap hwmap) {
        snatcher = hwmap.get(Servo.class, "snatcher");
    }

    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("snatcher", 0, 1, snatcher)
        );
    }

    void lift(){
        snatcher.setPosition(RAISED_POSITION);
    }
    void lower() {
        snatcher.setPosition(LOWERED_POSITION);
    }

}
