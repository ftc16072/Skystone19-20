package org.firstinspires.ftc.teamcode.opModesAuto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionDelayFor;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionDriveTo;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionRotateTo;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionSetPosition;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionSnatcher;
import org.firstinspires.ftc.teamcode.actions.QQ_AutoAction;
import org.firstinspires.ftc.teamcode.util.RobotPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Autonomous(name = "Waffle", group = "ftc16072")
public class AutoWaffle extends AutoBase {


    /**
     * overrides to Force placement in the build area
     *
     * @see AutoBase
     */
    @Override
    public void init_loop() {
        super.init_loop();
        startDepot = false;
    }

    /**
     * overrides to make sure the flipper stays up
     */
    @Override
    public void start() {
        super.start();
        robot.flipper.holdUp();
    }

    /**
     * @return list of steps based on alliance
     */
    List<QQ_AutoAction> getSteps() {
        List<QQ_AutoAction> steps = new ArrayList<>();
        QQ_ActionSetPosition startPosition =
                new QQ_ActionSetPosition(getStartPosition());
        if (redAlliance) {
            steps.addAll(Arrays.asList(
                    startPosition,
                    new QQ_ActionDriveTo(WAFFLE_RED_X - 2, WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionSnatcher(true),
                    new QQ_ActionDelayFor(1.25),
                    new QQ_ActionDriveTo(WAFFLE_RED_X + 14, WAFFLE_RED_Y - 5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(WAFFLE_RED_X + 14, WAFFLE_RED_Y + 12, DistanceUnit.INCH), //to square on wall
                    new QQ_ActionSetPosition(new RobotPosition(30, FIELD_BOUNDARIES - (WAFFLE_WIDTH + 9), DistanceUnit.INCH, 90, AngleUnit.DEGREES)),
                    new QQ_ActionSnatcher(false),
                    new QQ_ActionDelayFor(0.5)
            ));
        } else {
            steps.addAll(Arrays.asList(
                    startPosition,
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X - 2), WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionSnatcher(true),
                    new QQ_ActionDelayFor(1.25),
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 14), WAFFLE_RED_Y - 5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 14), WAFFLE_RED_Y + 12, DistanceUnit.INCH), //to square on wall
                    new QQ_ActionSetPosition(new RobotPosition(-30, FIELD_BOUNDARIES - (WAFFLE_WIDTH + 9), DistanceUnit.INCH, 90, AngleUnit.DEGREES)),
                    new QQ_ActionSnatcher(false),
                    new QQ_ActionDelayFor(0.5)
            ));
        }
        steps.addAll(getParkSteps());
        return steps;
    }

    /**
     * simplifies {@link #getSteps()} by moving parking commands out
     *
     * @return commands to park based on weather we are parking near or far
     */
    private List<QQ_AutoAction> getParkSteps() {
        if (farPark) {
            return Arrays.asList(
                    new QQ_ActionDriveTo(farPark_x, 0, DistanceUnit.INCH));
        } else {
            return Arrays.asList(
                    new QQ_ActionDriveTo(nearPark_x, FIELD_BOUNDARIES - (WAFFLE_WIDTH + 14), DistanceUnit.INCH),
                    new QQ_ActionDriveTo(nearPark_x, 0, DistanceUnit.INCH));
        }
    }
}