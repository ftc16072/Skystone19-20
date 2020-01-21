package org.firstinspires.ftc.teamcode.opModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.util.RobotPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Autonomous(name = "Skystone3", group = "ftc16072")
public class AutoSkystone3 extends AutoBase {
    /**
     * main body of Auto
     *
     * @return returns the steps for auto based on starting location
     */
    List<QQ_AutoAction> getSteps() {
        List<QQ_AutoAction> steps = new ArrayList<>();

        QQ_ActionSetPosition startPosition = new QQ_ActionSetPosition(getStartPosition());

        if (redAlliance) {
            steps.addAll(Arrays.asList(
                    startPosition,
                    new QQ_ActionRotator(0.0),
                    new QQ_ActionDriveTo(STONE_COLLECTION_RED_X, -28.5, DistanceUnit.INCH),
                    new QQ_ActionFlipper(70),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDelayFor(1),
                    new QQ_ActionDriveTo(STONE_COLLECTION_RED_X + 6, -28.5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(WAFFLE_RED_X + 3, WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(180, AngleUnit.DEGREES),
                    new QQ_ActionFlipper(-10),
                    new QQ_ActionDriveTo(WAFFLE_RED_X - 11, WAFFLE_RED_Y, DistanceUnit.INCH),
                    //new QQ_ActionFlipper(90),
                    new QQ_ActionPincer(false),
                    new QQ_ActionSnatcher(true),
                    new QQ_ActionDelayFor(1.25),
                    new QQ_ActionDriveTo((WAFFLE_RED_X + 18), WAFFLE_RED_Y - 5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo((WAFFLE_RED_X + 18), WAFFLE_RED_Y + 20, DistanceUnit.INCH), //to square on wall
                    new QQ_ActionSetPosition(new RobotPosition(36, FIELD_BOUNDARIES - (WAFFLE_WIDTH + 9), DistanceUnit.INCH, 90, AngleUnit.DEGREES)),
                    new QQ_ActionSnatcher(false)
            ));

        } else {
            steps.addAll(Arrays.asList(
                    startPosition,
                    new QQ_ActionRotator(0.0),
                    new QQ_ActionDriveTo(-STONE_COLLECTION_RED_X, -28.5, DistanceUnit.INCH),
                    new QQ_ActionFlipper(70),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDelayFor(1),
                    new QQ_ActionDriveTo(-(STONE_COLLECTION_RED_X + 6), -28.5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 5), WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(0, AngleUnit.DEGREES),
                    new QQ_ActionFlipper(-10),
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X - 11), WAFFLE_RED_Y, DistanceUnit.INCH),
                    //new QQ_ActionFlipper(90),
                    new QQ_ActionPincer(false),
                    new QQ_ActionSnatcher(true),
                    new QQ_ActionDelayFor(1.25),
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 18), WAFFLE_RED_Y - 5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 18), WAFFLE_RED_Y + 20, DistanceUnit.INCH), //to square on wall
                    new QQ_ActionSetPosition(new RobotPosition(-36, FIELD_BOUNDARIES - (WAFFLE_WIDTH + 9), DistanceUnit.INCH, 90, AngleUnit.DEGREES)),
                    new QQ_ActionSnatcher(false)
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