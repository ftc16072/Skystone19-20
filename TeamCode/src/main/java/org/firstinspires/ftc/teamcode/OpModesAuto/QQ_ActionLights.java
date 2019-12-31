package org.firstinspires.ftc.teamcode.OpModesAuto;


import android.support.annotation.ColorInt;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;


class QQ_ActionLights extends QQ_AutoAction {
    @ColorInt
    int color;

    /**
     * @param color what color to set the lights to as a Color Int
     * @see ColorInt
     */
    QQ_ActionLights(@ColorInt int color) {
        this.color = color;
    }

    /**
     * sets the lights
     * @param robot gives access to all robot functions
     * @param gameTime lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed
     */
    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        robot.robotLights.setColor(this.color);
        return true;
    }
}
