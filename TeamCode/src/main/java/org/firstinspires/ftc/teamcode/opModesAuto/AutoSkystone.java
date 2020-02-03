package org.firstinspires.ftc.teamcode.opModesAuto;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionActionList;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionActionPair;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionDelayFor;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionDriveTo;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionFlipper;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionLift;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionPincer;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionRotateTo;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionRotator;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionSetPosition;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionSnatcher;
import org.firstinspires.ftc.teamcode.actions.QQ_AutoAction;
import org.firstinspires.ftc.teamcode.util.RobotPosition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Config
@Autonomous(name = "Skystone", group = "ftc16072")
public class AutoSkystone extends AutoBase {
    SkystoneOpenCvPipeline pipeline = new SkystoneOpenCvPipeline();
    OpenCvCamera phoneCam;
    public static boolean useVision = false;

    @Override
    public void init() {
        super.init();
        if (useVision) {
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

            phoneCam.openCameraDevice();

            phoneCam.setPipeline(pipeline);
            phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        } else {
            pipeline.stoneLocation = 2;
        }
    }

    /**
     * overrides to Force placement in the depot area
     *
     * @see AutoBase
     */
    @Override
    public void init_loop() {
        super.init_loop();
        startDepot = true;
        telemetry.addData("Stone_Location", pipeline.stoneLocation);
        telemetry.update();
    }

    private double getStoneYPosition(int stoneLocation) {
        switch (stoneLocation) {
            case 1:
                return -44.5;
            case 2:
                return -36.5;
            case 3:
            default:
                return -28.5;
        }
    }

    private QQ_ActionActionList getSecondStoneSteps(int stoneLocation, boolean redAlliance) {
        int allianceMultiplier = redAlliance ? 1 : -1;
        int depoDirection = redAlliance ? 180 : 0;

        switch (stoneLocation) {
            case 1:
                return new QQ_ActionActionList("Stone 1", Arrays.asList(
                        new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 8), getStoneYPosition(1) - 24, DistanceUnit.INCH),
                        new QQ_ActionActionPair(
                                new QQ_ActionRotateTo(depoDirection, AngleUnit.DEGREES),
                                new QQ_ActionLift(2.0, DistanceUnit.INCH)
                        ),
                        new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X), getStoneYPosition(1) - 24, DistanceUnit.INCH),
                        new QQ_ActionPincer(true),
                        new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 6), getStoneYPosition(1) - 24, DistanceUnit.INCH)

                ));
            case 2:
            case 3:
            default:
                return new QQ_ActionActionList("Stone 2 or 3", Arrays.asList(
                        new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 8), getStoneYPosition(1) - 15, DistanceUnit.INCH),
                        new QQ_ActionActionPair(
                                new QQ_ActionRotateTo(depoDirection, AngleUnit.DEGREES),
                                new QQ_ActionLift(2.0, DistanceUnit.INCH)
                        ),
                        new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X), getStoneYPosition(1) - 20, DistanceUnit.INCH),
                        new QQ_ActionPincer(true),
                        new QQ_ActionDelayFor(1),
                        new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 6), getStoneYPosition(1), DistanceUnit.INCH)

                ));

        }
    }


    /**
     * @return list of steps based on alliance
     */
    List<QQ_AutoAction> getSteps() {
        List<QQ_AutoAction> steps = new ArrayList<>();
        QQ_ActionSetPosition startPosition =
                new QQ_ActionSetPosition(getStartPosition());
        QQ_ActionActionList secondStone = getSecondStoneSteps(pipeline.stoneLocation, redAlliance);
        if (redAlliance) {
            steps.addAll(Arrays.asList(
                    startPosition,
                    new QQ_ActionRotator(0.0),
                    new QQ_ActionFlipper(90),
                    new QQ_ActionActionPair(
                            new QQ_ActionLift(2.0, DistanceUnit.INCH),
                            new QQ_ActionDriveTo(STONE_COLLECTION_RED_X, getStoneYPosition(pipeline.stoneLocation), DistanceUnit.INCH)
                    ),
                    new QQ_ActionLift(0.0, DistanceUnit.CM),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDelayFor(1),
                    new QQ_ActionDriveTo(STONE_COLLECTION_RED_X + 6, getStoneYPosition(pipeline.stoneLocation), DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(WAFFLE_RED_X + 3, WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionActionPair(new QQ_ActionActionList("Moving", Arrays.asList(new QQ_ActionRotateTo(180, AngleUnit.DEGREES),
                            new QQ_ActionDriveTo(WAFFLE_RED_X - 11, WAFFLE_RED_Y, DistanceUnit.INCH))), new QQ_ActionLift(5.0, DistanceUnit.INCH)),
                    //new QQ_ActionFlipper(90),
                    new QQ_ActionPincer(false),
                    new QQ_ActionSnatcher(true),
                    new QQ_ActionDelayFor(1.25),
                    new QQ_ActionDriveTo((WAFFLE_RED_X + 18), WAFFLE_RED_Y - 5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo((WAFFLE_RED_X + 18), WAFFLE_RED_Y + 10, DistanceUnit.INCH), //to square on wall
                    new QQ_ActionSnatcher(false),
                    new QQ_ActionSetPosition(new RobotPosition((WAFFLE_RED_X + 24), FIELD_BOUNDARIES - (WAFFLE_WIDTH + 9), DistanceUnit.INCH, 90, AngleUnit.DEGREES)),
                    new QQ_ActionDriveTo(36, 24, DistanceUnit.INCH),
                    secondStone,
                    new QQ_ActionRotateTo(90.0, AngleUnit.DEGREES)

            ));
        } else {
            steps.addAll(Arrays.asList(
                    startPosition,
                    new QQ_ActionRotator(0.0),
                    new QQ_ActionFlipper(90),
                    new QQ_ActionLift(2.0, DistanceUnit.INCH),
                    new QQ_ActionDriveTo(-STONE_COLLECTION_RED_X, getStoneYPosition(pipeline.stoneLocation), DistanceUnit.INCH),
                    new QQ_ActionLift(0.0, DistanceUnit.CM),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDelayFor(1),
                    new QQ_ActionDriveTo(-(STONE_COLLECTION_RED_X + 6), getStoneYPosition(pipeline.stoneLocation), DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 3), WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionActionPair(new QQ_ActionActionList("Moving", Arrays.asList(new QQ_ActionRotateTo(0, AngleUnit.DEGREES),
                            new QQ_ActionDriveTo(-(WAFFLE_RED_X - 11), WAFFLE_RED_Y, DistanceUnit.INCH))), new QQ_ActionLift(5.0, DistanceUnit.INCH)),
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

        //steps.addAll(getParkSteps());
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
                    new QQ_ActionDriveTo(farPark_x, 24, DistanceUnit.INCH),
                    new QQ_ActionDriveTo(farPark_x, 24, DistanceUnit.INCH));
        } else {
            return Arrays.asList(
                    new QQ_ActionDriveTo(nearPark_x, 24, DistanceUnit.INCH),
                    new QQ_ActionDriveTo(nearPark_x, 0, DistanceUnit.INCH));
        }
    }
}