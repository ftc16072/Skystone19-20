package org.firstinspires.ftc.teamcode.OpModesAuto;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

class QQ_ActionSnatcher extends QQ_AutoAction {
    private Boolean snatch;

    QQ_ActionSnatcher(Boolean snatch) {
        this.snatch = snatch;
    }

    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (snatch) {
            robot.lowerSnatcher();
            return true;
        } else {
            robot.liftSnatcher();
            return true;
        }
    }
}
