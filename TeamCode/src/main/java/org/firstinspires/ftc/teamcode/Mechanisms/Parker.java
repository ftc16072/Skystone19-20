package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.motors.NeveRest3_7GearmotorV1;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;
import java.util.List;

class Parker {
    private static double SPEED = 0.6;
    private DcMotor parker;

    void init(HardwareMap hwMap) {
        parker = hwMap.get(DcMotor.class, "flipper");
        parker.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    List<QQ_Test> getTests() {
        NeveRest3_7GearmotorV1 motor;
        return Arrays.asList(
                new QQ_TestMotor("Parker", 0.2, parker)
        );
    }

    void out() {
        parker.setPower(SPEED);
    }

    void stop() {
        parker.setPower(0.0);
    }
}
