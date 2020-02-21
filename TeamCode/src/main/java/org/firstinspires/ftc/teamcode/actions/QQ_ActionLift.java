package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionLift extends QQ_AutoAction {
    double location;
    DistanceUnit distanceUnit;

    /**
     *
     * @param location the desired location of the lift
     * @param distanceUnit the distance units the location is given in
     */
    public QQ_ActionLift(Double location, DistanceUnit distanceUnit) {
        this.location = location;
        this.distanceUnit = distanceUnit;
    }

    /**
     * moves the lift to desired location
     *
     * @param robot     gives access to all robot functions
     * @param gameTime  lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns True when the lifter reaches the desired location
     */
    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        return robot.lifter.goToPosition(location, distanceUnit);
    }
}
