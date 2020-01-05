package org.firstinspires.ftc.teamcode.opModesAuto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionLift extends QQ_AutoAction {
    double location;
    DistanceUnit distanceUnit;

    QQ_ActionLift(Double location, DistanceUnit distanceUnit) {
        this.location = location;
        this.distanceUnit = distanceUnit;
    }

    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        return robot.lifter.goToPosition(location, distanceUnit);
    }
}
