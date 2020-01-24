package org.firstinspires.ftc.teamcode.QQ_Actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionLiftToNextBlock extends QQ_AutoAction {
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        return robot.lifter.liftToPlacing();
    }
}
