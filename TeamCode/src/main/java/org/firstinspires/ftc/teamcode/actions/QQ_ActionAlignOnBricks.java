package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionAlignOnBricks extends QQ_AutoAction {

    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        boolean leftGood = (robot.lifter.leftDistanceSensor.getDistance(DistanceUnit.CM) >= robot.lifter.middleDistanceSensor.getDistance(DistanceUnit.CM) + robot.lifter.DISTANCE_SENSOR_TOLERANCE);
        boolean rightGood = (robot.lifter.leftDistanceSensor.getDistance(DistanceUnit.CM) >= robot.lifter.middleDistanceSensor.getDistance(DistanceUnit.CM) + robot.lifter.DISTANCE_SENSOR_TOLERANCE);
        if(leftGood && rightGood){
            return true;
        }
        if(leftGood && !rightGood){
            robot.nav.strafe(0.6);
        }
        if(rightGood && !leftGood){
            robot.nav.strafe(-0.6);
        }
        return false;
    }
}
