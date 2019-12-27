package org.firstinspires.ftc.teamcode.OpModesAuto;


import android.support.annotation.ColorInt;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;


class QQ_ActionLights extends QQ_AutoAction {
    @ColorInt
    int color;

    QQ_ActionLights(@ColorInt int color) {
        this.color = color;
    }

    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        robot.robotLights.setColor(this.color);
        return true;
    }
}
