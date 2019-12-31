package org.firstinspires.ftc.teamcode.OpModesTeleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Util.Polar;

@TeleOp()
public class DriveOnly extends OpMode {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    private static double MAX_SPEED = 0.6;
    private Robot robot = new Robot();
    private boolean pincerOpen = false;
    private boolean xPressed = false;
    private boolean snatcherOpen = true;
    private boolean bPressed = false;
    private static double FAST_LIFT = 1.0;
    private static double LIFT = 0.7;

    /**
     * initilizes robot and sets max speed to 0.6
     */
    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.nav.setMecanumDriveMaxSpeed(MAX_SPEED);
    }

    
    /**
     * squares but maintains the sign
     * @param x what number to square
     * @return x squared without loosing the sign
     */
    private double squareWithSign(double x) {
        return x * x * Math.signum(x);
    }

    /**
     * figures out how fast to strafe based on the trigger
     * @param trigger how far down is the trigger pressed
     * @return how fast to strafe 
     */
    private double strafeFromTrigger(double trigger) {
        return trigger / 2; //halve the speed from the trigger
    }

    /**
     * Contains all the driving controls
     */
    private void driverLoop() {
        //squaring driving joystick to make it less sensitive in the middle
        double forward = squareWithSign(gamepad1.left_stick_y * -1); //The y direction on the gamepad is reversed idk why
        double strafe = squareWithSign(gamepad1.left_stick_x);
        if (gamepad1.a) {
            robot.quack();
        } // :)

        // if turbo bumper is pressed, allow full speed
        if (gamepad1.right_bumper || gamepad1.left_bumper) {
            robot.nav.setMecanumDriveMaxSpeed(1);
        } else {
            robot.nav.setMecanumDriveMaxSpeed(MAX_SPEED);
        }
        Polar g1RightJoystick = Polar.fromCartesian(gamepad1.right_stick_x, -gamepad1.right_stick_y);

        double r = g1RightJoystick.getR();
        if (gamepad1.right_trigger >= 0.05) {
            robot.nav.strafe(strafeFromTrigger(gamepad1.right_trigger));
        } else if (gamepad1.left_trigger >= 0.05) {
            robot.nav.strafe(-strafeFromTrigger(gamepad1.left_trigger));
        } else {
            if (r >= 0.8) {
                robot.nav.driveFieldRelativeAngle(strafe, forward, g1RightJoystick.getTheta());
            } else {
                robot.nav.driveFieldRelative(strafe, forward, 0.0);
            }
        }
        //Snatcher Code
        if (gamepad1.b && !bPressed) {
            snatcherOpen = !snatcherOpen;
        }
        bPressed = gamepad1.b;

        if (snatcherOpen) {
            robot.liftSnatcher();
            telemetry.addData("snatcher", "Lifted");
        } else {
            robot.lowerSnatcher();
            telemetry.addData("snatcher", "Lowered");
        }

    }

    /**
     * Contains all the manipulator controlsd
     */
    private void manipulatorLoop() {
        Polar g2RightJoystick = Polar.fromCartesian(gamepad2.right_stick_x, -gamepad2.right_stick_y);

        //Pincer Code
        if (gamepad2.right_trigger >= 0.5 || gamepad2.left_trigger >= 0.5) {
            robot.openPincer();
        } else {
            robot.closePincer();
        }

        //Rotator Code
        if (gamepad2.x) {
            robot.setRotator(-90, AngleUnit.DEGREES, telemetry);
        } else if (gamepad2.y) {
            robot.setRotator(0, AngleUnit.DEGREES, telemetry);
        } else if (gamepad2.b) {
            robot.setRotator(90, AngleUnit.DEGREES, telemetry);
        } else if (g2RightJoystick.getR() >= 0.8) { //TODO make field relative
            telemetry.addData("Joystick Angle: ", AngleUnit.normalizeDegrees(g2RightJoystick.getDegrees() - 90));
            robot.setRotator(g2RightJoystick.getTheta() - ((Math.PI) / 2), AngleUnit.RADIANS, telemetry);
        }

        //Flipper Code
        if (gamepad2.dpad_up) {
            robot.setFlipper(Robot.FlipperPositions.UP);
        } else if (gamepad2.dpad_down) {
            robot.setFlipper(Robot.FlipperPositions.DOWN);
        } else {
            robot.setFlipper(Robot.FlipperPositions.STOP);
        }

        //Lift Code
        //TODO add take lift to bottom if A is pressed
        if (Math.abs(gamepad2.left_stick_y) >= 0.1) {
            robot.moveLifter(squareWithSign(gamepad2.left_stick_y * -1));
        } else { //Stop Lift
            robot.moveLifter(0);
        }


    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    /**
     * runs the {@link #driverLoop()} runs the {@link #manipulatorLoop()}
     */
    @Override
    public void loop() {
        driverLoop();
        manipulatorLoop();
    }
}
