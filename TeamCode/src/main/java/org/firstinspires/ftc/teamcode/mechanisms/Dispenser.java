package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;

public class Dispenser {
    private static final double HOLD_POSITION_LOWER = 0.45;
    private static final double DUMP_POSITION_LOWER = 0.8;
    private static final double HOLD_POSITION_UPPER = 0.63;
    private static final double DUMP_POSITION_UPPER = 0.76;
    private Servo dispenserLower;
    private Servo dispenserUpper;

    /**
     * initializes the dispensers
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
     * @return returns a list of test that extends the dispensers
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestServo("Dispenser_Lower", HOLD_POSITION_LOWER, DUMP_POSITION_LOWER, dispenserLower),
                new QQ_TestServo("Dispenser_Upper", HOLD_POSITION_UPPER, DUMP_POSITION_UPPER, dispenserUpper)
        );
    }

    /**
     * dump the dispensers
     */
    public void dump() {
        dispenserLower.setPosition(DUMP_POSITION_LOWER);
        dispenserUpper.setPosition(DUMP_POSITION_UPPER);
    }

    /**
     * hold the dispensers
     */
    public void hold() {
        dispenserLower.setPosition(HOLD_POSITION_LOWER);
        dispenserUpper.setPosition(HOLD_POSITION_UPPER);
    }
}
