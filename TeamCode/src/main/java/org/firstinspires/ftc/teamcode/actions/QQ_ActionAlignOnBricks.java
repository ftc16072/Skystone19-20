package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionAlignOnBricks extends QQ_AutoAction {

    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if((robot.lifter.leftDistanceSensor.getDistance(DistanceUnit.CM) >= robot.lifter.middleDistanceSensor.getDistance(DistanceUnit.CM) + robot.lifter.DISTANCE_SENSOR_TOLERANCE) && (robot.lifter.leftDistanceSensor.getDistance(DistanceUnit.CM) >= robot.lifter.middleDistanceSensor.getDistance(DistanceUnit.CM) + robot.lifter.DISTANCE_SENSOR_TOLERANCE)){
            return true;
        }
        return false;
    }
}
