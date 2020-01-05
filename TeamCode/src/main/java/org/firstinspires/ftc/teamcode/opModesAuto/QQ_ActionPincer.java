package org.firstinspires.ftc.teamcode.opModesAuto;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

class QQ_ActionPincer extends QQ_AutoAction {
    private Boolean pincer;

    /**
     * pincer -- returns true right away... to wait for it to finish, wait ~1.25 sec after to lower
     *
     * @param pinch raise or lower -- true = lower; false = raise
     */
    QQ_ActionPincer(Boolean pinch) {
        this.pincer = pinch;
    }

    /**
     * starts the pincer (should delay ~1.25 to allow snatching to happen)
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed
     */
    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (pincer) {
            robot.pincer.close();
            return true;
        } else {
            robot.pincer.open();
            return true;
        }
    }
}
