package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;


class Snatcher {
    private double RIGHT_LOWERED = 0.6;
    private double RIGHT_RAISED = 1.0;
    private Servo snatcherRight;
    private double LEFT_LOWERED = 1.0;
    private double LEFT_RAISED = 0.6;
    private Servo snatcherLeft;

    void init(HardwareMap hwmap) {
        snatcherRight = hwmap.get(Servo.class, "snatcher_right");
        snatcherLeft = hwmap.get(Servo.class, "snatcher_left");
    }

    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("snatcher_right", RIGHT_RAISED, RIGHT_LOWERED, snatcherRight) ,
                new QQ_TestServo("snatcher_left", LEFT_RAISED, LEFT_LOWERED, snatcherLeft)
        );
    }

    void lift(){
        snatcherRight.setPosition(RIGHT_RAISED);
        snatcherLeft.setPosition(LEFT_RAISED);
    }
    void lower() {
        snatcherRight.setPosition(RIGHT_LOWERED);
        snatcherLeft.setPosition(LEFT_LOWERED);
    }

}
