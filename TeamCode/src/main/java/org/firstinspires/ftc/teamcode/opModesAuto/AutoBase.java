package org.firstinspires.ftc.teamcode.opModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;
import org.firstinspires.ftc.teamcode.actions.QQ_AutoAction;
import org.firstinspires.ftc.teamcode.util.RobotPosition;

import java.util.List;

abstract public class AutoBase extends OpMode {
    final Robot robot = new Robot();
    OpModeManager mgr;

    private List<QQ_AutoAction> autoSteps;
    private int stepNum;

    boolean startDepot = true;
    boolean redAlliance = true;
    boolean farPark = false;
    double farPark_x;
    double nearPark_x;


    private boolean aPressed;
    private boolean xPressed;
    private boolean yPressed;

    static final double START_DEPOT_Y = -36;
    static final double START_BUILD_Y = 36;
    static final double BLUE_START_X = -63;
    static final double FAR_PARK_RED_X = 36;
    static final double NEAR_PARK_RED_X = 62;
    static final double WAFFLE_RED_X = 32;
    static final double WAFFLE_RED_Y = 48;
    static final double WAFFLE_WIDTH = 18.5;
    static final double FIELD_BOUNDARIES = 72;
    static final double STONE_COLLECTION_RED_X = 38.5;

    /**
     * allows for the user to select settings from:
     * Start depot  --  Start build
     * Red Alliance --  Blue Alliance
     * Near park    --  Far park
     */
    public void init_loop() {
        fitIn18();
        if (gamepad1.a & !aPressed) {
            startDepot = !startDepot;
        }
        aPressed = gamepad1.a;
        if (gamepad1.x & !xPressed) {
            redAlliance = !redAlliance;
            robot.robotLights.allianceLights(!redAlliance);
        }
        xPressed = gamepad1.x;
        if (gamepad1.y & !yPressed) {
            farPark = !farPark;
        }
        yPressed = gamepad1.y;

        telemetry.addData("A = StartDepot, x = Alliance, y = Park", "");
        telemetry.addData("Settings", "\n%s, %s, %s",
                startDepot ? "Depot" : "Build",
                redAlliance ? "Red" : "Blue",
                farPark ? "Far" : "Near");

        telemetry.addData("Place robot at:", "%.0f in %.0f in", getStartPosition().getX(DistanceUnit.INCH), getStartPosition().getY(DistanceUnit.INCH));
    }

    /**
     * @return RobotPosition based on the settings set in init_Loop
     */
    RobotPosition getStartPosition() {
        double startX = BLUE_START_X;
        double startY;
        double heading = 0;

        if (redAlliance) {
            startX = -1 * startX;
            heading = 180;
        }
        Robot.nav.setRedAlliance(false);

        if (startDepot) {
            startY = START_DEPOT_Y;
        } else {
            startY = START_BUILD_Y;
        }
        return new RobotPosition(startX, startY, DistanceUnit.INCH, heading, AngleUnit.DEGREES);
    }

    /**
     * Initializes robot
     */
    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.robotLights.allianceLights(!redAlliance);
    }

    /**
     * makes the robot fit in 18'
     */
    private void fitIn18() {
        robot.snatcher.lift();
        //robot.rotator.rotate(-90, AngleUnit.DEGREES, telemetry);
        robot.rotator.fit(telemetry);
        robot.pincer.fit();
/*

        if (robot.lifter.downdistance.getDistance(DistanceUnit.CM) < 5.5 || robot.lifter.getEncoderPosition() <= 0) {
            robot.lifter.move(0.0);
        } else {
            robot.lifter.move(-0.2);
        }
*/

        if (robot.flipper.getPosition() >= -70) {
            robot.flipper.up();
        } else {
            robot.flipper.stop();
        }

    }

    /**
     * forces all autos to have a getSteps() method
     *
     * @return List of QQ Auto Actions
     */
    abstract List<QQ_AutoAction> getSteps();

    /**
     * set the far and near park variables based on the alliance
     * get the steps and set the index to 0
     */
    @Override
    public void start() {
        if (redAlliance) {
            farPark_x = FAR_PARK_RED_X;
            nearPark_x = NEAR_PARK_RED_X;
        } else {
            farPark_x = FAR_PARK_RED_X * -1;
            nearPark_x = NEAR_PARK_RED_X * -1;
        }
        autoSteps = getSteps();
        stepNum = 0;

    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP

    /**
     * steps through the list of actions, waiting till they are true to advance
     * print current step to the telemetry and to the system log
     * print finished when done
     */
    @Override
    public void loop() {
        if (stepNum < autoSteps.size()) {
            QQ_AutoAction step = autoSteps.get(stepNum);
            telemetry.addData("auto", stepNum);
            if (step.run(robot, time, telemetry)) {
                stepNum++;
                System.out.printf("QQ_Step %d finished at %f\n", stepNum, time);
            }
        } else {
            telemetry.addData("auto", "Finished");
        }
    }

    @Override
    public void stop() {
        Robot.nav.setRedAlliance(redAlliance);
    }
}