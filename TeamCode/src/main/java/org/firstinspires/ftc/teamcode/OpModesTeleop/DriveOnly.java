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
    private boolean snatcherOpen = false;
    private boolean bPressed = false;
    private static double FAST_LIFT = 1.0;
    private static double LIFT = 0.7;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.setMecanumDriveMaxSpeed(MAX_SPEED);
    }

    private double squareWithSign(double x) {
        return x * x * Math.signum(x);
    }

    private double strafeFromTrigger(double trigger) {
        return trigger / 2; //halve the speed from the trigger
    }

    private void driverLoop() {
        //squaring driving joystick to make it less sensitive in the middle
        double forward = squareWithSign(gamepad1.left_stick_y * -1); //The y direction on the gamepad is reversed idk why
        double strafe = squareWithSign(gamepad1.left_stick_x);
        if (gamepad1.a) {
            robot.quack();
        } // :)

        // if turbo bumper is pressed, allow full speed
        if (gamepad1.right_bumper || gamepad1.left_bumper) {
            robot.setMecanumDriveMaxSpeed(1);
        } else {
            robot.setMecanumDriveMaxSpeed(MAX_SPEED);
        }
        Polar g1RightJoystick = Polar.fromCartesian(gamepad1.right_stick_x, -gamepad1.right_stick_y);

        double r = g1RightJoystick.getR();
        if (gamepad1.right_trigger >= 0.05) {
            robot.strafe(strafeFromTrigger(gamepad1.right_trigger));
        } else if (gamepad1.left_trigger >= 0.05) {
            robot.strafe(-strafeFromTrigger(gamepad1.left_trigger));
        } else {
            if (r >= 0.8) {
                robot.driveFieldRelativeAngle(strafe, forward, g1RightJoystick.getTheta());
            } else {
                robot.driveFieldRelative(strafe, forward, 0.0);
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
            telemetry.addData("Joystick Angle: ", g2RightJoystick.getDegrees());
            robot.setRotator(g2RightJoystick.getTheta(), AngleUnit.RADIANS, telemetry);
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
    @Override
    public void loop() {
        driverLoop();
        manipulatorLoop();
    }
}
