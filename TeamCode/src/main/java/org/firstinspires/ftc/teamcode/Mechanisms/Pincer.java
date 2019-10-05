package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;


class Pincer {
    private double OPEN_POSITION = 0.0;
    private double CLOSE_POSITION = 1.0;
    private Servo pincer;

    void init(HardwareMap hwmap) {
        pincer = hwmap.get(Servo.class, "pincer");
    }

    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("Pincer", 0, 1,pincer)
        );
    }

    void open(){
        pincer.setPosition(OPEN_POSITION);
    }
    void close() {
        pincer.setPosition(CLOSE_POSITION);
    }

}
