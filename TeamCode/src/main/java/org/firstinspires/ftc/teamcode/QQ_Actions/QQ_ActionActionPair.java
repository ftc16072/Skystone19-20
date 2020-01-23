package org.firstinspires.ftc.teamcode.QQ_Actions;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;


public class QQ_ActionActionPair extends QQ_AutoAction {
    private QQ_AutoAction action1;
    private QQ_AutoAction action2;

    /**
     * @param action1 first action
     * @param action2 second action
     */
    public QQ_ActionActionPair(QQ_AutoAction action1, QQ_AutoAction action2) {
        this.action1 = action1;
        this.action2 = action2;
    }

    /**
     * delays for specified time
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        boolean action1Done = action1.run(robot,gameTime,telemetry);
        boolean action2Done = action2.run(robot,gameTime,telemetry);
        if(action1Done && action2Done){ // I'm unhappy about this but lazy execution is annoying
            return true;
        } else {
            return false;
        }
    }
}
