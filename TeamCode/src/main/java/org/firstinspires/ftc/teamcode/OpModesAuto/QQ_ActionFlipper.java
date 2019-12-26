package org.firstinspires.ftc.teamcode.OpModesAuto;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

class QQ_ActionFlipper extends QQ_AutoAction {
    private int position;

    QQ_ActionFlipper(int position) {
        this.position = position;
    }

    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (position > 0) {
            return true;
        } else {
            robot.setFlipper(Robot.FlipperPositions.STOP);
            return true;
        }
    }
}
