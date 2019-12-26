package org.firstinspires.ftc.teamcode.OpModesAuto;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

class QQ_ActionRotateTo extends QQ_AutoAction {
    double angleRadians;

    QQ_ActionRotateTo(double angle, AngleUnit angleUnit) {
        angleRadians = angleUnit.toRadians(angle);
    }

    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        robot.nav.setMecanumDriveMaxSpeed(0.8);
        if (robot.nav.rotateTo(angleRadians, AngleUnit.RADIANS)) {
            robot.nav.setMecanumDriveMaxSpeed(1);
            return true;
        }
        return false;
    }
}