package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;

public class Dispenser {
    private static final double HOLD_POSITION_LOWER = 0.45;
    private static final double DUMP_POSITION_LOWER = 0.8;
    private static final double HOLD_POSITION_UPPER = 0;
    private static final double DUMP_POSITION_UPPER = 1;
    private Servo dispenserLower;
    private Servo dispenserUpper;

    /**
     * initializes the dispenserLower
     *
     * @param hwMap hardware map from the config
     */
    void init(HardwareMap hwMap) {
        dispenserLower = hwMap.get(Servo.class, "dispenserLower");
        dispenserUpper = hwMap.get(Servo.class, "dispenserUpper");
    }

    /**
     * gets test for test wiring
     *
     * @return returns a test that extends the dispenserLower
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("Dispenser_Lower", HOLD_POSITION_LOWER, DUMP_POSITION_LOWER, dispenserLower),
                new QQ_TestServo("Dispenser_Upper", HOLD_POSITION_UPPER, DUMP_POSITION_UPPER, dispenserUpper)
        );
    }

    /**
     * extends dispenserLower
     */
    public void dump() {
        dispenserLower.setPosition(DUMP_POSITION_LOWER);
        dispenserUpper.setPosition(DUMP_POSITION_UPPER);
    }

    /**
     * stops the dispenserLower
     */
    public void hold() {
        dispenserLower.setPosition(HOLD_POSITION_LOWER);
        dispenserUpper.setPosition(HOLD_POSITION_UPPER);
    }
}
