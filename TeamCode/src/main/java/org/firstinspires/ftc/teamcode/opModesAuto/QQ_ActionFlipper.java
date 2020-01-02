package org.firstinspires.ftc.teamcode.opModesAuto;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

class QQ_ActionFlipper extends QQ_AutoAction {
    private int position;

    /**
     * @param position what position to set flipper to -- <0 means stop (currently does nothing else)
     */
    QQ_ActionFlipper(int position) {
        this.position = position;
    }

    /**
     * Flips Flipper
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed
     */
    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (position > 0) {
            return true;
        } else {
            robot.flipper.stop();
            return true;
        }
    }
}
