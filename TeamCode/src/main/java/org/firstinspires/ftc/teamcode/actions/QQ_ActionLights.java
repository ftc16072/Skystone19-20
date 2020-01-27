package org.firstinspires.ftc.teamcode.actions;


import android.support.annotation.ColorInt;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;


public class QQ_ActionLights extends QQ_AutoAction {
    @ColorInt
    private int color;

    /**
     * @param color what color to set the lights to as a Color Int
     * @see ColorInt
     */
    public QQ_ActionLights(@ColorInt int color) {
        this.color = color;
    }

    /**
     * sets the lights
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        robot.robotLights.setColor(this.color);
        return true;
    }
}
