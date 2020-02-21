package org.firstinspires.ftc.teamcode.actions;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionRotator extends QQ_AutoAction {
    private Double rotate;

    /**
     * rotates -- returns true right away... to wait for it to finish, wait ~1.25 sec after to lower
     *
     * @param rotate what angle to spin to
     */
    public QQ_ActionRotator(Double rotate) {
        this.rotate = rotate;
    }

    /**
     * starts the rotator (should delay ~1.25 to allow snatching to happen)
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true instantly
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        robot.rotator.rotate(rotate, AngleUnit.DEGREES, telemetry);
        return true;
    }
}
