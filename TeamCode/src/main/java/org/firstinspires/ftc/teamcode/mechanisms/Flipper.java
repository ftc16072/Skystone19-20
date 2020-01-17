package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;
import java.util.List;

public class Flipper {
    private static final double SPEED_UP = -0.9;
    private static final double SPEED_DOWN = 0.4;
    private DcMotor flipper;

    /**
     * This initializes our Flipper.
     * it makes sure that it will break at 0
     * it then makes sure it won't move much
     *
     * @param hwMap This is the Hardware map from the configuration
     */
    void init(HardwareMap hwMap) {
        flipper = hwMap.get(DcMotor.class, "flipper");
        flipper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flipper.setPower(0.0);
    }

    /**
     * This gives us flipper tests for test wiring
     *
     * @return a flipper up and flipper down test for test wiring
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestMotor("Flipper - Up", SPEED_UP, flipper),
                new QQ_TestMotor("Flipper - Down", SPEED_DOWN, flipper)
        );
    }

    /**
     * Moves the flipper down
     */
    public void down() {
        flipper.setPower(SPEED_DOWN);
    }

    /**
     * Moves the flipper up
     */
    public void up() {
        flipper.setPower(SPEED_UP);
    }

    /**
     * Stops the flipper
     */
    public void stop() {
        flipper.setPower(0.0);
    }

    /**
     * Keeps the flipper held against the top of the lift for autos
     */
    public void holdUp() {
        flipper.setPower(-0.2);
    }

    /**
     * gets encoder ticks of flipper
     * @return encoder position of flipper
     */
    public double getPosition() {
        return flipper.getCurrentPosition();
    }
}

