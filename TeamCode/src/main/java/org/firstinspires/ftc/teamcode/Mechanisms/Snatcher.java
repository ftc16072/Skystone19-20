package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;


class Snatcher {
    private double RIGHT_LOWERED = 0.5;
    private double RIGHT_RAISED = 1.0;
    private Servo snatcherRight;
    private double LEFT_LOWERED = 1.0;
    private double LEFT_RAISED = 0.5;
    private Servo snatcherLeft;

    /**
     * Initilizes the snatcher
     * @param hwmap hardware map from the config
     */
    void init(HardwareMap hwmap) {
        snatcherRight = hwmap.get(Servo.class, "snatcher_right");
        snatcherLeft = hwmap.get(Servo.class, "snatcher_left");
    }

    /**
     * @return a list of tests one for the right snatcher and one for the left snatcher
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("snatcher_right", RIGHT_RAISED, RIGHT_LOWERED, snatcherRight) ,
                new QQ_TestServo("snatcher_left", LEFT_RAISED, LEFT_LOWERED, snatcherLeft)
        );
    }
    /**
     * Lifts the snatchers
     */
    void lift(){
        snatcherRight.setPosition(RIGHT_RAISED);
        snatcherLeft.setPosition(LEFT_RAISED);
    }
    /**
     * Lowers the snatchers
     */
    void lower() {
        snatcherRight.setPosition(RIGHT_LOWERED);
        snatcherLeft.setPosition(LEFT_LOWERED);
    }

}
