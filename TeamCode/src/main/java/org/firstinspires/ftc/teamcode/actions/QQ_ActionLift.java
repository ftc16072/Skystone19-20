package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionLift extends QQ_AutoAction {
    double location;
    DistanceUnit distanceUnit;

    /**
     * Moves the lifter in accordance with the current location and the distance to the new location
     *
     * @param location the current location of the lift
     * @param distanceUnit the distance units measuring the remaining distance to move
     */
    public QQ_ActionLift(Double location, DistanceUnit distanceUnit) {
        this.location = location;
        this.distanceUnit = distanceUnit;
    }

    /**
     * Determines where the lift is supposed to be by using robot functions
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns the position which the lifter is approaching
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        return robot.lifter.goToPosition(location, distanceUnit);
    }
}
