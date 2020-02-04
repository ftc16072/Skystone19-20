package org.firstinspires.ftc.teamcode.actions;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionDriveTo extends QQ_AutoAction {
    private double x;
    private double y;
    private DistanceUnit distanceUnit;

    /**
     * @param x            x coordinate to drive to
     * @param y            y coordinate to drive to
     * @param distanceUnit unit coordinates are in (In, Cm)
     */
    public QQ_ActionDriveTo(double x, double y, DistanceUnit distanceUnit) {
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
     * @return returns true when completed
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        telemetry.addData("Position x", robot.nav.getEstimatedPosition().getX(DistanceUnit.INCH));
        telemetry.addData("Positon y", robot.nav.getEstimatedPosition().getY(DistanceUnit.INCH));
        return robot.nav.driveTo(x, y, distanceUnit);
    }
}
