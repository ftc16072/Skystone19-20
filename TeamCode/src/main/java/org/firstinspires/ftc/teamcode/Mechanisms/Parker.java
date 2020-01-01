package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.motors.NeveRest3_7GearmotorV1;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;
import java.util.List;

public class Parker {
    private static double SPEED = -0.6;
    private DcMotor parker;
    /**
     * inistializes the parker
     * @param hwMap hardware map from the config
     */
    void init(HardwareMap hwMap) {
        parker = hwMap.get(DcMotor.class, "parker");
        parker.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * gets test for test wiring
     * @return returns a test that extends the parker
     */
    List<QQ_Test> getTests() {
        NeveRest3_7GearmotorV1 motor;
        return Arrays.asList(
                new QQ_TestMotor("Parker", SPEED, parker)
        );
    }

    /**
     * extends parker
     */
   public void out() {
        parker.setPower(SPEED);
    }

    /**
     * stops the parker
     */
    public void stop() {
        parker.setPower(0.0);
    }
}
