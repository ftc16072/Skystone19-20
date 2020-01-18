package org.firstinspires.ftc.teamcode.opModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.util.RobotPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Autonomous(name = "Skystone1", group = "ftc16072")
public class AutoSkystone1 extends AutoBase {
    /**
     * main body of Auto
     *
     * @return returns the steps for auto based on starting location
     */
    List<QQ_AutoAction> getSteps() {
        List<QQ_AutoAction> steps = new ArrayList<>();
        QQ_ActionSetPosition startPosition =
                new QQ_ActionSetPosition(getStartPosition());
        if (redAlliance) {
            steps.addAll(Arrays.asList(
                    startPosition,
                    new QQ_ActionRotator(0.0),
                    new QQ_ActionFlipper(1),
                    new QQ_ActionDriveTo(STONE_COLLECTION_RED_X, -44.5, DistanceUnit.INCH),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDriveTo(FAR_PARK_RED_X, -44.5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(WAFFLE_RED_X + 3, WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(180, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(WAFFLE_RED_X, WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionPincer(false)

            ));
        }
        return steps;
    }
}