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
        return false;
    }
}
