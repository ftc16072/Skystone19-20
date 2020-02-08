package org.firstinspires.ftc.teamcode.actions;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

import java.util.List;


public class QQ_ActionActionList extends QQ_AutoAction {
    private List<QQ_AutoAction> actionList;
    private int actionStepNum = 0;
    private String description;

    /**
     * @param actionList list of actions to complete
     */
    public QQ_ActionActionList(String description, List<QQ_AutoAction> actionList) {
        this.actionList = actionList;
        this.description = description;
    }

    /**
     * steps through the list and returns true when its done
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (actionStepNum < actionList.size()) {
            QQ_AutoAction step = actionList.get(actionStepNum);
            if (step.run(robot, gameTime, telemetry)) {
                actionStepNum++;
                telemetry.addData(description + " -- Step", actionStepNum);
            }
            return false;
        } else {
            return true;
        }
    }
}
