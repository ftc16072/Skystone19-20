package org.firstinspires.ftc.teamcode.opModesAuto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionDelayFor;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionDriveTo;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionDriveToRelative;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionFlipper;
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

@Autonomous(name = "Skystone", group = "ftc16072")
public class AutoSkystone extends AutoBase {
    SkystoneOpenCvPipeline pipeline = new SkystoneOpenCvPipeline();
    OpenCvCamera phoneCam;
    public static boolean useVision = false;
    public int stoneLocation = 1;

    @Override
    public void init() {
        if (useVision){
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);\

            phoneCam.openCameraDevice();

            phoneCam.setPipeline(pipeline);
            phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        } else {
            pipeline.stoneLocation = stoneLocation;
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
        stoneLocation = pipeline.stoneLocation;
        telemetry.addData("Stone_Location", stoneLocation);
        telemetry.update();
    }

    /**
     * overrides to make sure the flipper stays up
     */
    @Override
    public void start() {
        super.start();
        robot.flipper.holdUp();
    }

    private double getStoneYPosition(int stoneLocation){
        switch (stoneLocation){
            case 1: {
                return -44.5;
            }
            case 2: {
                return -36.5;
            }
            case 3: {
                return -28.5;
            }
        }
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
                    new QQ_ActionRotator(0.0),
                    new QQ_ActionDriveTo(STONE_COLLECTION_RED_X, getStoneYPosition(stoneLocation), DistanceUnit.INCH),
                    new QQ_ActionFlipper(70),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDelayFor(1),
                    new QQ_ActionDriveTo(STONE_COLLECTION_RED_X + 6, getStoneYPosition(stoneLocation), DistanceUnit.INCH),
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
                    new QQ_ActionDriveTo(-STONE_COLLECTION_RED_X, getStoneYPosition(stoneLocation), DistanceUnit.INCH),
                    new QQ_ActionFlipper(70),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDelayFor(1),
                    new QQ_ActionDriveTo(-(STONE_COLLECTION_RED_X + 6), getStoneYPosition(stoneLocation), DistanceUnit.INCH),
                    new QQ_ActionRotateTo(90, AngleUnit.DEGREES),
                    new QQ_ActionDriveTo(-(WAFFLE_RED_X + 10), WAFFLE_RED_Y, DistanceUnit.INCH),
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