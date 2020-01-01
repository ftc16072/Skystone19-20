package org.firstinspires.ftc.teamcode.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.motors.NeveRest3_7GearmotorV1;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;

@Config
public class Dispenser {
    public static double HOLD_POSITION = 0.45;
    public static double DUMP_POSITION = 0.8;
    private Servo dispenser;

    /**
     * inistializes the dispenser
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
