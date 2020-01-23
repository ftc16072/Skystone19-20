package org.firstinspires.ftc.teamcode.QQ_Actions;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionSnatcher extends QQ_AutoAction {
    private Boolean snatch;

    /**
     * snatches -- returns true right away... to wait for it to finish, wait ~1.25 sec after to lower
     *
     * @param snatch raise or lower -- true = lower; false = raise
     */
    public QQ_ActionSnatcher(Boolean snatch) {
        this.snatch = snatch;
    }

    /**
     * starts the snatchers (should delay ~1.25 to allow snatching to happen)
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (snatch) {
            robot.snatcher.lower();
            return true;
        } else {
            robot.snatcher.lift();
            return true;
        }
    }
}
