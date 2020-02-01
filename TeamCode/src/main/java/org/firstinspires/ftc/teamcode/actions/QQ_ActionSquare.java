package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionSquare extends QQ_AutoAction {
    QQ_ActionRotateTo rotate = new QQ_ActionRotateTo(90, AngleUnit.DEGREES);
    double tolerance = 3;
    public static double DISTANCE_KP = 1;

    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        if (rotate.run(robot, gameTime, telemetry)){
            double average = (robot.baseLeft.getDistance(DistanceUnit.CM) + robot.baseRight.getDistance(DistanceUnit.CM))/2;
            double diff = 27 - average;
            if(Math.abs(diff) <= tolerance){
                return true;
            }

            robot.nav.mecanumDrive.driveMecanum(diff*DISTANCE_KP, 0, 0);
        }
        return false;
    }
}
