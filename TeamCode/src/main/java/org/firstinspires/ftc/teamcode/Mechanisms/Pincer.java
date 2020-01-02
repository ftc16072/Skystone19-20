package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;


public class Pincer {
    private double OPEN_POSITION = 0.0;
    private double CLOSE_POSITION = 1.0;
    private Servo pincer;

    /**
     * Inits the pincer
     * @param hwmap hardware map from the config
     */
    void init(HardwareMap hwmap) {
        pincer = hwmap.get(Servo.class, "pincer");
    }

    /**
     * returns tests for get test
     * @return returns a test that opens when held and closes when released
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("Pincer", 0, 1,pincer)
        );
    }

    /**
     * opens the pincer
     */
    public void open() {
        pincer.setPosition(OPEN_POSITION);
    }

    /**
     * closes the pincer
     */
    public void close() {
        pincer.setPosition(CLOSE_POSITION);
    }

}
