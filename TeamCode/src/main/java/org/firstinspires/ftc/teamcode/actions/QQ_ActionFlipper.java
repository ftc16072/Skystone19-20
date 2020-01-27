package org.firstinspires.ftc.teamcode.actions;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionFlipper extends QQ_AutoAction {
    private double position;

    /**
     * @param position what position to set flipper to -- <0 means stop (currently does nothing else)
     */
    public QQ_ActionFlipper(double position) {
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
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        return robot.flipper.goToDegree(position, telemetry);
    }
}
