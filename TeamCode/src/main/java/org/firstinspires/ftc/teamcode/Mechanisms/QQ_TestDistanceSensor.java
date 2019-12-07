package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

class QQ_TestDistanceSensor extends QQ_Test {
    private DistanceSensor distanceSensor;

    QQ_TestDistanceSensor(String description, DistanceSensor distanceSensor) {
        super(description);
        this.distanceSensor = distanceSensor;
    }


    @Override
    void run(boolean on, Telemetry telemetry) {
        if (on) {
            telemetry.addData("Distance (in):", distanceSensor.getDistance(DistanceUnit.INCH));
        } else {
            telemetry.addData("Distance (cm):", distanceSensor.getDistance(DistanceUnit.CM));
        }
    }
}
