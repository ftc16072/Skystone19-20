package org.firstinspires.ftc.teamcode.actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

public class QQ_ActionSquare extends QQ_AutoAction {
    double tolerance = 3;
    public static double DISTANCE_KP = 0.03;
    double DISTANCE_FROM_PLATFORM_CM = 27;
    double SLOWEST_SPEED = 0.2;

    @Override
    public boolean run(Robot robot, double gameTime, Telemetry telemetry) {

        double average = (robot.baseLeft.getDistance(DistanceUnit.CM) + robot.baseRight.getDistance(DistanceUnit.CM)) / 2;
        if (average > 400) {
            average = Math.min(robot.baseLeft.getDistance(DistanceUnit.CM), robot.baseRight.getDistance(DistanceUnit.CM));
        }
        if (average > 400) {
            return true; //both sensors not working we give up
        }
        double diff = average - DISTANCE_FROM_PLATFORM_CM;
        telemetry.addData("left_distance", robot.baseLeft.getDistance(DistanceUnit.CM));
        telemetry.addData("right_distance", robot.baseRight.getDistance(DistanceUnit.CM));
        if (Math.abs(diff) <= tolerance) {
            Robot.nav.mecanumDrive.driveMecanum(0, 0, 0);
            return true;
        }

        Robot.nav.mecanumDrive.driveMecanum(Math.signum(diff) * Math.max(SLOWEST_SPEED, Math.abs(DISTANCE_KP * diff)), 0, 0);

        return false;
    }
}
