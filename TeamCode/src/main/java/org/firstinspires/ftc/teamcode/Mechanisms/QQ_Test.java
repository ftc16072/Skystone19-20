package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.robotcore.external.Telemetry;

abstract class QQ_Test {
    private String description;

    QQ_Test(String description) {
        this.description = description;
    }

    String getDescription() {
        return description;
    }

    abstract void run(boolean on, Telemetry telemetry);

}
