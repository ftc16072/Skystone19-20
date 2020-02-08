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
import java.util.concurrent.atomic.AtomicReference;

@Config
@Autonomous(name = "Skystone", group = "ftc16072")
public class AutoSkystone extends AutoBase {
    SkystoneOpenCvPipeline pipeline = new SkystoneOpenCvPipeline();
    OpenCvCamera phoneCam;
    public static boolean useVision = true;
    private static double STONE_1_Y_POS = -25;
    private static double STONE_WIDTH = 8;

    /**
     * Initializes the camera and robot
     */
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
     * stops the camera
     */
    @Override
    public void start() {
        super.start();
        phoneCam.stopStreaming();

    }

    /**
     * overrides to Force placement in the depot area and far park
     *
     * @see AutoBase
     */
    @Override
    public void init_loop() {
        super.init_loop();
        startDepot = true;
        farPark = true;
        telemetry.addData("Stone_Location", pipeline.stoneLocation);
        telemetry.update();
    }

    /**
     * gets the y position for the stone based on its location
     *
     * @param stoneLocation what location is the stone in: 1, 2, or 3
     * @return returns stone y position
     */
    private double getStoneYPosition(int stoneLocation) {
        if (redAlliance) {
            switch (stoneLocation) {
                case 1:
                    return STONE_1_Y_POS - (2 * STONE_WIDTH);
                case 2:
                    return STONE_1_Y_POS - STONE_WIDTH;
                case 3:
                default:
                    return STONE_1_Y_POS;
            }
        } else {
            switch (stoneLocation) { // blue alliance stones go the other way
                case 1:
                    return STONE_1_Y_POS;
                case 2:
                    return STONE_1_Y_POS - STONE_WIDTH;
                case 3:
                default:
                    return STONE_1_Y_POS - (2 * STONE_WIDTH);
            }
        }
    }

    /**
     * gets the steps for the second stone
     * @param stoneLocation which position was the stone in
     * @param redAlliance are we on red alliance
     * @return returns a QQ_actionList with the steps
     */
    private QQ_ActionActionList getSecondStoneSteps(int stoneLocation, boolean redAlliance) {
        int allianceMultiplier = redAlliance ? 1 : -1; // x is flipped for blue alliance
        int depoDirection = redAlliance ? 180 : 0; // rotation is inverted
        if (redAlliance) {
            switch (stoneLocation) {
                case 1:
                    return new QQ_ActionActionList("Stone 1", Arrays.asList(
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 8), getStoneYPosition(3) - 24, DistanceUnit.INCH), // drive to the right location for a random stone
                            new QQ_ActionActionPair(
                                    new QQ_ActionRotateTo(depoDirection, AngleUnit.DEGREES),
                                    new QQ_ActionLift(3.0, DistanceUnit.INCH)
                            ), // rotate and lift lift in parallel to save time
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X), getStoneYPosition(3) - 24, DistanceUnit.INCH), // drive in to the right location to collect the stone
                            new QQ_ActionPincer(true),
                            new QQ_ActionDelayFor(1), // grab the stone
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 4), getStoneYPosition(3) - 24, DistanceUnit.INCH) // back up so as to disturb bricks less

                    ));
                case 2:
                case 3:
                default: //case 2 and 3 and default are all the same
                    return new QQ_ActionActionList("Stone 2 or 3", Arrays.asList(
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 8), getStoneYPosition(stoneLocation) - 26, DistanceUnit.INCH), // drive to correct location
                            new QQ_ActionActionPair(
                                    new QQ_ActionRotateTo(depoDirection, AngleUnit.DEGREES),
                                    new QQ_ActionLift(3.0, DistanceUnit.INCH)
                            ), // lift and turn at the same time
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X), getStoneYPosition(stoneLocation) - 24, DistanceUnit.INCH), // go to the correct x to grab stone
                            new QQ_ActionPincer(true),
                            new QQ_ActionDelayFor(1), // grab stone delay to let servo close
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 4), getStoneYPosition(stoneLocation) - 24, DistanceUnit.INCH) // back up to lessen the disturbance of bricks

                    ));
            }

        } else {
            switch (stoneLocation) {
                case 3:
                    return new QQ_ActionActionList("Stone 1", Arrays.asList(
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 8), getStoneYPosition(1) - 24, DistanceUnit.INCH), // drive to location of 3rd stone
                            new QQ_ActionActionPair(
                                    new QQ_ActionRotateTo(depoDirection, AngleUnit.DEGREES),
                                    new QQ_ActionLift(3.0, DistanceUnit.INCH)
                            ), // lift and turn at the same time
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X), getStoneYPosition(1) - 24, DistanceUnit.INCH), // drive to the correct location to intake a brick
                            new QQ_ActionPincer(true),
                            new QQ_ActionDelayFor(1), // grab brick (allowing servo to close)
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 4), getStoneYPosition(1) - 24, DistanceUnit.INCH) // lessen disturbance of the other bricks

                    ));
                case 2:
                case 1:
                default: // case 2 and 1 and default are the same
                    return new QQ_ActionActionList("Stone 2 or 3", Arrays.asList(
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 2), getStoneYPosition(stoneLocation) - 24, DistanceUnit.INCH), // drive to correct y for the brick
                            new QQ_ActionActionPair(
                                    new QQ_ActionRotateTo(depoDirection, AngleUnit.DEGREES),
                                    new QQ_ActionLift(3.0, DistanceUnit.INCH)
                            ), // lift and spin at the same time to decrease time used
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X), getStoneYPosition(stoneLocation) - 24, DistanceUnit.INCH), // go to correct x to intake stone
                            new QQ_ActionPincer(true),
                            new QQ_ActionDelayFor(1), // grab block (waits for servo to close)
                            new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 4), getStoneYPosition(stoneLocation) - 24, DistanceUnit.INCH))); // lessens the disturbance of the blocks


            }
        }
    }

    /**
     * simplifies {@link #getSteps()} by moving parking commands out
     *
     * @return commands to park based on weather we are parking near or far
     */
    private List<QQ_AutoAction> getParkSteps() {
        return Arrays.asList(
                new QQ_ActionDriveTo(farPark_x, 24, DistanceUnit.INCH),
                new QQ_ActionDriveTo(farPark_x, 0, DistanceUnit.INCH));
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
                    new QQ_ActionRotator(0.0), // position rotater to correct location
                    new QQ_ActionActionPair(
                            new QQ_ActionActionPair(
                                    new QQ_ActionLift(3.0, DistanceUnit.INCH),
                                    new QQ_ActionFlipper(90)
                            ),
                            new QQ_ActionDriveTo(STONE_COLLECTION_RED_X, getStoneYPosition(pipeline.stoneLocation), DistanceUnit.INCH)
                    ), // Lift the lift, flip the flipper and drive to the right spot all at the same time
                    new QQ_ActionLift(0.0, DistanceUnit.CM),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDelayFor(1), // grab block
                    new QQ_ActionDriveTo(STONE_COLLECTION_RED_X + 4, getStoneYPosition(pipeline.stoneLocation), DistanceUnit.INCH), // lessen the disturbance of other blocks
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES), // don't hit alliance partner
                    new QQ_ActionDriveTo(WAFFLE_RED_X, WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionActionPair(
                            new QQ_ActionActionList("Moving", Arrays.asList(
                                    new QQ_ActionRotateTo(180, AngleUnit.DEGREES),
                                    new QQ_ActionDriveTo(WAFFLE_RED_X - 12, WAFFLE_RED_Y, DistanceUnit.INCH))),
                            new QQ_ActionLift(11.0, DistanceUnit.INCH)), // lift while we turn and drive
                    //new QQ_ActionFlipper(90),
                    new QQ_ActionPincer(false),
                    new QQ_ActionSnatcher(true),
                    new QQ_ActionDelayFor(1.25), // release brick and grab foundation
                    new QQ_ActionDriveTo((WAFFLE_RED_X + 18), WAFFLE_RED_Y - 5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo((WAFFLE_RED_X + 18), WAFFLE_RED_Y + 10, DistanceUnit.INCH), //back up and square on wall
                    new QQ_ActionSnatcher(false),
                    new QQ_ActionSetPosition(new RobotPosition((WAFFLE_RED_X + 24), FIELD_BOUNDARIES - (WAFFLE_WIDTH + 9), DistanceUnit.INCH, 90, AngleUnit.DEGREES)), // reset where the robot thinks it is
                    new QQ_ActionDriveTo(37, 24, DistanceUnit.INCH),// clear alliance partner
                    secondStone, // run second stone steps
                    new QQ_ActionRotateTo(90.0, AngleUnit.DEGREES) // dont hit alliance partner on the way back


            ));
        } else {
            steps.addAll(Arrays.asList(
                    startPosition,
                    new QQ_ActionRotator(0.0), // position rotater to correct location
                    new QQ_ActionActionPair(
                            new QQ_ActionActionPair(
                                    new QQ_ActionLift(3.0, DistanceUnit.INCH),
                                    new QQ_ActionFlipper(90)
                            ),
                            new QQ_ActionDriveTo(-STONE_COLLECTION_RED_X, getStoneYPosition(pipeline.stoneLocation), DistanceUnit.INCH)
                    ), // Lift the lift, flip the flipper and drive to the right spot all at the same time
                    new QQ_ActionLift(0.0, DistanceUnit.CM),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDelayFor(1), // 1st stone grabbed
                    new QQ_ActionDriveTo(-(STONE_COLLECTION_RED_X + 4), getStoneYPosition(pipeline.stoneLocation), DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES), // don't hit alliance partner
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 8), WAFFLE_RED_Y, DistanceUnit.INCH),
                    new QQ_ActionActionPair(
                            new QQ_ActionActionList("Moving", Arrays.asList(
                                    new QQ_ActionRotateTo(0, AngleUnit.DEGREES),
                                    new QQ_ActionDriveTo(-(WAFFLE_RED_X - 8), WAFFLE_RED_Y, DistanceUnit.INCH))),
                            new QQ_ActionLift(11.0, DistanceUnit.INCH)), // lift while we turn and drive
                    //new QQ_ActionFlipper(90),
                    new QQ_ActionPincer(false),
                    new QQ_ActionSnatcher(true), // waffle snagged
                    new QQ_ActionDelayFor(1.25), // release brick and grab foundation
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 20), WAFFLE_RED_Y - 5, DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 20), WAFFLE_RED_Y + 10, DistanceUnit.INCH), //back up and square on wall
                    new QQ_ActionSnatcher(false),
                    new QQ_ActionSetPosition(new RobotPosition(-(WAFFLE_RED_X + 13), FIELD_BOUNDARIES - (WAFFLE_WIDTH + 9), DistanceUnit.INCH, 90, AngleUnit.DEGREES)), // reset where the robot thinks it is
                    new QQ_ActionDriveTo(-40, 24, DistanceUnit.INCH), // clear alliance partner
                    secondStone,// run second stone steps
                    new QQ_ActionRotateTo(90.0, AngleUnit.DEGREES) // dont hit alliance partner on the way back
            ));
        }
        steps.addAll(getParkSteps()); // run park steps
        return steps;
    }

}