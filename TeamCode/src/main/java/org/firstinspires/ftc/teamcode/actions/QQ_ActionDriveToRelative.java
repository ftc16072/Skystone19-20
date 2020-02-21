package org.firstinspires.ftc.teamcode.actions;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;
import org.firstinspires.ftc.teamcode.util.RobotPosition;

public class QQ_ActionDriveToRelative extends QQ_AutoAction {
    private double x;
    private double y;
    private boolean started;
    private DistanceUnit distanceUnit;

    /**
     * @param x            x offset to drive to
     * @param y            y offset to drive to
     * @param distanceUnit unit coordinates are in (In, Cm)
     */
    public QQ_ActionDriveToRelative(double x, double y, DistanceUnit distanceUnit) {
        this.x = x;
        this.y = y;
        this.distanceUnit = distanceUnit;
    }

    /**
     * drives to location
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when driving is completed
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (!started) {
            RobotPosition robotPosition = robot.nav.getEstimatedPosition();
            x = robotPosition.getX(distanceUnit) + x;
            y = robotPosition.getY(distanceUnit) + y;
            started = true;
        }
        return robot.nav.driveTo(x, y, distanceUnit);
    }
}
