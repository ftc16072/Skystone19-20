package org.firstinspires.ftc.teamcode.mechanisms;

import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class QQ_TestLights extends QQ_Test {
    private QwiicLEDStrip ledStrip;

    /**
     * @param description string describing the test
     * @param ledStrip led strip to be used when testing
     */
    QQ_TestLights(String description, QwiicLEDStrip ledStrip) {
        super(description);
        this.ledStrip = ledStrip;
    }

    /**
     * sets the lights to Blue or Red
     * @param on determines blue or red -- true = blue; false = red
     * @param telemetry is required from abstract method
     */
    @Override
    void run(boolean on, Telemetry telemetry) {
        if (on) {
            ledStrip.setColor(Color.rgb(0, 0, 255));
        } else {
            ledStrip.setColor(Color.rgb(255, 0, 0));
        }
    }
}
