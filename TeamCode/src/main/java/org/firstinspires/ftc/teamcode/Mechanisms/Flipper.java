package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;
import java.util.List;

class Flipper {
    private static double SPEED_UP = -0.7;
    private static double SPEED_DOWN = 0.1;
    private DcMotor flipper;
    void init(HardwareMap hwMap){
        flipper = hwMap.get(DcMotor.class, "flipper");
        flipper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestMotor("Flipper - Up", SPEED_UP, flipper),
                new QQ_TestMotor("Flipper - Down", SPEED_DOWN, flipper)
        );
    }
    void down(){
        flipper.setPower(SPEED_DOWN);
    }
    void up(){
        flipper.setPower(SPEED_UP);
    }
    void stop(){
        flipper.setPower(0.0);
    }
}
