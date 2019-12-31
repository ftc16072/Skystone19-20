package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Util.RobotPosition;

import java.util.List;

abstract public class QQ_AutoBase extends OpMode {
    protected Robot robot = new Robot();

    private List<QQ_AutoAction> autoSteps;
    private int stepNum;

    protected boolean startDepot = true;
    protected boolean redAlliance = true;
    protected boolean farPark = true;
    protected double farPark_x;
    protected double nearPark_x;


    private boolean aPressed;
    private boolean xPressed;
    private boolean yPressed;

    static double START_DEPOT_Y = -36;
    static double START_BUILD_Y = 36;
    static double BLUE_START_X = -63;
    static double FAR_PARK_RED_X = 36;
    static double NEAR_PARK_RED_X = 62;
    static double WAFFLE_RED_X = 32;
    static double WAFFLE_RED_Y = 48;

    /**
     * allows for the user to select settings from:
     *             Start depot  --  Start build
     *             Red Alliance --  Blue Alliance
     *             Near park    --  Far park
     */
    public void init_loop() {
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
    protected RobotPosition getStartPosition() {
        double startX = BLUE_START_X;
        double startY = 0;
        double heading = 0;

        if (redAlliance) {
            startX = -1 * startX;
            heading = 180;
        }

        if (startDepot) {
            startY = START_DEPOT_Y;
        } else {
            startY = START_BUILD_Y;
        }
        return new RobotPosition(startX, startY, DistanceUnit.INCH, heading, AngleUnit.DEGREES);
    }

    /**
     * Init's robot
     */
    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.robotLights.allianceLights(false);
    }

    /**
     * forces all autos to have a getsteps method
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
}