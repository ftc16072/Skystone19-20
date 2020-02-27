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
public class AutoSkystoneRefactored extends AutoBase {
    SkystoneOpenCvPipeline pipeline = new SkystoneOpenCvPipeline();
    OpenCvCamera phoneCam;
    public static boolean useVision = true;
    public static boolean continuing = true;
    public static double yReset = FIELD_BOUNDARIES - (WAFFLE_WIDTH + 9);
    private static double STONE_1_Y_POS = -25;
    private static double STONE_WIDTH = 8;
    double xReset;

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
        if (useVision) {
            phoneCam.stopStreaming();
        }
        robot.nav.setMecanumDriveMaxSpeed(1);

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
        if (redAlliance) {
            xReset = 54;
        } else {
            xReset = 44;
        }
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
     * Autonomous used to detect and grab the skystone.
     *
     * @param stonePosition Stone position is used in tne program to locate the skystones in the list of stones and to grab them correctly.
     * @return Returns the amount of stones that has been collected to be used later in the program and to find out which auto to continue running.
     */

    private QQ_ActionActionList grabStoneSteps(double stonePosition) {
        int allianceMultiplier = redAlliance ? 1 : -1;
        int stoneDirection = redAlliance ? 180 : 0;
        QQ_ActionActionList stoneSteps = new QQ_ActionActionList("Grab Stone", Arrays.asList(
                new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 8), stonePosition, DistanceUnit.INCH), // drive to the right location for a random stone
                new QQ_ActionPincer(false),
                new QQ_ActionRotator(0.0),
                new QQ_ActionActionPair(
                        new QQ_ActionRotateTo(stoneDirection, AngleUnit.DEGREES),
                        new QQ_ActionActionPair(
                                new QQ_ActionLift(3.0, DistanceUnit.INCH),
                                new QQ_ActionFlipper(90))
                ), // rotate and lift lift in parallel to save time
                new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X), stonePosition, DistanceUnit.INCH), // drive in to the right location to collect the stone
                new QQ_ActionPincer(true),
                new QQ_ActionDelayFor(1), // grab the stone
                new QQ_ActionDriveTo(allianceMultiplier * (STONE_COLLECTION_RED_X + 4), stonePosition, DistanceUnit.INCH))); // back up so as to disturb bricks less
        return stoneSteps;
    }


    /**
     * gets the steps for the second stone
     *
     * @param stoneLocation which position was the stone in
     * @return returns a QQ_actionList with the steps
     */
    private QQ_ActionActionList getSecondStoneSteps(int stoneLocation) {
        if (redAlliance) {
            switch (stoneLocation) {
                case 1:
                    return new QQ_ActionActionList("Stone 1", Arrays.asList(
                            grabStoneSteps(getStoneYPosition(3))

                    ));
                case 2:
                case 3:
                default: //case 2 and 3 and default are all the same
                    return new QQ_ActionActionList("Stone 2 or 3", Arrays.asList(
                            grabStoneSteps(getStoneYPosition(stoneLocation) - 24)
                    ));
            }

        } else {
            switch (stoneLocation) {
                case 3:
                    return new QQ_ActionActionList("Stone 1", Arrays.asList(
                            grabStoneSteps(getStoneYPosition(1))
                    ));
                case 2:
                case 1:
                default: // case 2 and 1 and default are the same
                    return new QQ_ActionActionList("Stone 2 or 3", Arrays.asList(
                            grabStoneSteps(getStoneYPosition(stoneLocation) - 24)));

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
                new QQ_ActionRotateTo(90.0, AngleUnit.DEGREES),  // dont hit alliance partner on the way back
                new QQ_ActionDriveTo(farPark_x, 24, DistanceUnit.INCH),
                new QQ_ActionDriveTo(farPark_x, 0, DistanceUnit.INCH));
    }


    /**
     * Snatches the foundation and places the skystone collected on the foundation.
     *
     * @return returns the new action list for the robot to compile and execute.
     */
    private QQ_ActionActionList getWaffleSteps() {
        int allianceMultiplier = redAlliance ? 1 : -1;
        int waffleDirection = redAlliance ? 180 : 0;
        return new QQ_ActionActionList("Waffle", Arrays.asList(
                new QQ_ActionDriveTo(allianceMultiplier * WAFFLE_RED_X, WAFFLE_RED_Y, DistanceUnit.INCH),
                new QQ_ActionActionPair(
                        new QQ_ActionActionList("Moving", Arrays.asList(
                                new QQ_ActionRotateTo(waffleDirection, AngleUnit.DEGREES),
                                new QQ_ActionDriveTo(allianceMultiplier * (WAFFLE_RED_X - 12), WAFFLE_RED_Y, DistanceUnit.INCH))),
                        new QQ_ActionLift(11.0, DistanceUnit.INCH)), // lift while we turn and drive
                //new QQ_ActionFlipper(90),
                new QQ_ActionPincer(false),
                new QQ_ActionSnatcher(true),
                new QQ_ActionDelayFor(1.25), // release brick and grab foundation
                new QQ_ActionDriveTo(allianceMultiplier * (WAFFLE_RED_X + 18), WAFFLE_RED_Y - 5, DistanceUnit.INCH),
                new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                new QQ_ActionDriveTo(allianceMultiplier * (WAFFLE_RED_X + 18), WAFFLE_RED_Y + 10, DistanceUnit.INCH), //back up and square on wall
                new QQ_ActionSnatcher(false),
                new QQ_ActionSetPosition(new RobotPosition(allianceMultiplier * xReset, yReset, DistanceUnit.INCH, 90, AngleUnit.DEGREES)) // reset where the robot thinks it is
        ));
    }
    /**
     * Follows a certain point on the field and finds out how to get back to that location to grab the second skystone.
     *
     * @return list of steps based on alliance
     */
    List<QQ_AutoAction> getSteps() {
        List<QQ_AutoAction> steps = new ArrayList<>();
        QQ_ActionSetPosition startPosition =
                new QQ_ActionSetPosition(getStartPosition());
        steps.addAll(Arrays.asList(startPosition,
                grabStoneSteps(getStoneYPosition(pipeline.stoneLocation)),
                new QQ_ActionRotateTo(90, AngleUnit.DEGREES), // don't hit alliance partner
                getWaffleSteps()));
        if (continuing) {
            QQ_ActionActionList secondStone = getSecondStoneSteps(pipeline.stoneLocation);
            if (redAlliance) {
                steps.addAll(Arrays.asList(
                        new QQ_ActionActionPair(new QQ_ActionDriveTo(37, 24, DistanceUnit.INCH), new QQ_ActionLift(0.0, DistanceUnit.CM)), // clear alliance partner
                        secondStone// run second stone steps


                ));
            } else {
                steps.addAll(Arrays.asList(
                        new QQ_ActionActionPair(new QQ_ActionDriveTo(-40, 24, DistanceUnit.INCH), new QQ_ActionLift(0.0, DistanceUnit.CM)), // clear alliance partner
                        secondStone// run second stone steps
                ));
            }
            steps.addAll(getParkSteps()); // run park steps
        }
        return steps;
    }

}