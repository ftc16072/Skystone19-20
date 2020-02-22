package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;


public class Pincer {
    private static final double OPEN_POSITION = 0.33;
    private static final double CLOSE_POSITION = 1.0;
    private Servo pincer;

    /**
     * Initializes the pincer
     *
     * @param hwmap hardware map from the config
     */
    void init(HardwareMap hwmap) {
        pincer = hwmap.get(Servo.class, "pincer");
    }

    /**
     * returns tests for get test
     *
     * @return returns a test that opens when held and closes when released
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("Pincer", 1, 0.33, pincer)
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
    /**
     * makes the pincer go to the required position to fit in 18"
     */
    public void fit(){pincer.setPosition(0.0);}

}
