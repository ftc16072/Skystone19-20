package org.firstinspires.ftc.teamcode.Mechanisms;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

class QQ_TestLights extends QQ_Test {
    private QwiicLEDStrip ledStrip;

    QQ_TestLights(String description, QwiicLEDStrip ledStrip) {
        super(description);
        this.ledStrip = ledStrip;
    }


    @Override
    void run(boolean on, Telemetry telemetry) {
        if (on) {
            ledStrip.setColor(Color.rgb(0, 0, 255));
        } else {
            ledStrip.setColor(Color.rgb(255, 0, 0));
        }
    }
}
