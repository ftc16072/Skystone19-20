package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;
import org.firstinspires.ftc.teamcode.util.RobotPosition;

public class QQ_ActionSetPosition extends QQ_AutoAction {
    private double x;
    private double y;
    private double supposedAngle;

    /**
     * @param robotPosition what position should the robot be set to as a robot position
     */
    public QQ_ActionSetPosition(RobotPosition robotPosition) {
        this.x = robotPosition.getX(DistanceUnit.CM);
        this.y = robotPosition.getY(DistanceUnit.CM);
        this.supposedAngle = robotPosition.getHeading(AngleUnit.RADIANS);
    }

    /**
     * Sets robot position
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        robot.nav.setPosition(x, y, DistanceUnit.CM);
        robot.nav.resetIMU(supposedAngle, AngleUnit.RADIANS);
        return true;
    }
}
