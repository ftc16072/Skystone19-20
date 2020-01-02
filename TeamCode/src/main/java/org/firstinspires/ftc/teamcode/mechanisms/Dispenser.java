package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;

public class Dispenser {
    private static final double HOLD_POSITION = 0.45;
    private static final double DUMP_POSITION = 0.8;
    private Servo dispenser;

    /**
     * initializes the dispenser
     *
     * @param hwMap hardware map from the config
     */
    void init(HardwareMap hwMap) {
        dispenser = hwMap.get(Servo.class, "dispenser");
    }

    /**
     * gets test for test wiring
     *
     * @return returns a test that extends the dispenser
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("Dispenser", HOLD_POSITION, DUMP_POSITION, dispenser)
        );
    }

    /**
     * extends dispenser
     */
    public void dump() {
        dispenser.setPosition(DUMP_POSITION);
    }

    /**
     * stops the dispenser
     */
    public void hold() {
        dispenser.setPosition(HOLD_POSITION);
    }
}
