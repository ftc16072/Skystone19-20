package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Arrays;
import java.util.List;


class Lifter {
    private DcMotor lift;
    private DistanceSensor downdistance;

    void init(HardwareMap hwmap){
        lift = hwmap.get(DcMotor.class, "lifter");
        downdistance = hwmap.get(DistanceSensor.class, "downward_distance");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestMotor("Lift-Down", -0.2, lift),
                new QQ_TestMotor("lift-Up", 0.2, lift)
        );
    }

    void move(double speed){
        if(speed < 0){
            if (downdistance.getDistance(DistanceUnit.CM) <= 12){
                speed = 0;
            }
        }
        lift.setPower(speed);
    }
    int getPosiition(){
        return lift.getCurrentPosition();
    }
}
