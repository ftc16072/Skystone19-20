package org.firstinspires.ftc.teamcode.actions;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;


public class QQ_ActionDelayFor extends QQ_AutoAction {
    private double endTime;
    private double timeDelay;

    /**
     * @param timeDelay how long to delay for in sec
     */
    public QQ_ActionDelayFor(double timeDelay) {
        this.timeDelay = timeDelay;
    }

    /**
     * delays for specified time
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when time has expired
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (endTime == 0.0) {
            endTime = gameTime + timeDelay;
        }
        if (gameTime >= endTime) {
            return true;
        }
        return false;
    }
}
