package org.firstinspires.ftc.teamcode.OpModesAuto;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

class QQ_ActionRotateTo extends QQ_AutoAction {
    double angleRadians;

    /**
     * @param angle what angle to turn to
     * @param angleUnit what unit is angle in
     */
    QQ_ActionRotateTo(double angle, AngleUnit angleUnit) {
        angleRadians = angleUnit.toRadians(angle);
    }

    /**
     * turns to angle
     * @param robot gives access to all robot functions
     * @param gameTime lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed
     */
    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (robot.nav.rotateTo(angleRadians, AngleUnit.RADIANS)) {
            return true;
        }
        return false;
    }
}