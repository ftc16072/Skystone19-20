package org.firstinspires.ftc.teamcode.opModesTeleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;
import org.firstinspires.ftc.teamcode.util.Polar;

@Disabled
@TeleOp()
public class DriveOnlyOLD extends OpMode {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    private static final double MAX_SPEED = 0.6;
    private final Robot robot = new Robot();
    private boolean snatcherOpen = true;
    private boolean bPressed = false;


    /**
     * initializes robot and sets max speed to 0.6
     */
    @Override
    public void init() {
        telemetry.addData("State", "Initializing");
        telemetry.update();
        robot.init(hardwareMap);
        robot.nav.setMecanumDriveMaxSpeed(MAX_SPEED);
        robot.robotLights.allianceLights(!robot.nav.isRedAlliance());
        telemetry.addData("State", "Quacktastic");
    }


    /**
     * squares but maintains the sign
     *
     * @param x what number to square
     * @return x squared without loosing the sign
     */
    private double squareWithSign(double x) {
        return x * x * Math.signum(x);
    }

    /**
     * figures out how fast to strafe based on the trigger
     *
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
                telemetry.addData("joystick angle", g1RightJoystick.getTheta());
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
            robot.snatcher.lift();
            telemetry.addData("snatcher", "Lifted");
        } else {
            robot.snatcher.lower();
            telemetry.addData("snatcher", "Lowered");
        }

        if (gamepad1.x) {
            robot.dispenser.dump();
        } else {
            robot.dispenser.hold();
        }

        if (gamepad1.y && gamepad1.left_bumper && gamepad1.right_bumper) {
            robot.parker.out();
        } else {
            robot.parker.stop();
        }

        if (gamepad1.dpad_down) {
            robot.nav.resetIMU(180, AngleUnit.DEGREES);
        } else if (gamepad1.dpad_up) {
            robot.nav.resetIMU(0, AngleUnit.DEGREES);
        } else if (gamepad1.dpad_left) {
            robot.nav.resetIMU(-90, AngleUnit.DEGREES);
        } else if (gamepad1.dpad_right) {
            robot.nav.resetIMU(90, AngleUnit.DEGREES);
        }


    }

    /**
     * Contains all the manipulator controls
     */
    private void manipulatorLoop() {
        Polar g2RightJoystick = Polar.fromCartesian(gamepad2.right_stick_x, -gamepad2.right_stick_y);

        //Pincer Code
        if (gamepad2.right_trigger >= 0.5 || gamepad2.left_trigger >= 0.5) {
            robot.pincer.open();
        } else {
            robot.pincer.close();
        }

        //Rotator Code
        if (gamepad2.x) {
            robot.rotator.rotate(-90, AngleUnit.DEGREES, telemetry);
        } else if (gamepad2.y) {
            robot.rotator.rotate(0, AngleUnit.DEGREES, telemetry);
        } else if (gamepad2.b) {
            robot.rotator.rotate(90, AngleUnit.DEGREES, telemetry);
        } else if (g2RightJoystick.getR() >= 0.8) { //TODO make field relative
            telemetry.addData("Joystick Angle: ", AngleUnit.normalizeDegrees(g2RightJoystick.getDegrees() - 90));
            robot.rotator.rotate(g2RightJoystick.getTheta() - ((Math.PI) / 2), AngleUnit.RADIANS, telemetry);
        }

        //Flipper Code
        if (gamepad2.dpad_up) {
            robot.flipper.up();
        } else if (gamepad2.dpad_down) {
            robot.flipper.down();
        } else {
            robot.flipper.stop();
        }

        //Lift Code
        //TODO add take lift to bottom if A is pressed
        if (Math.abs(gamepad2.left_stick_y) >= 0.1) {
            robot.lifter.move(squareWithSign(gamepad2.left_stick_y * -1));
        } else { //Stop Lift
            robot.lifter.move(0);
        }


    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP

    /**
     * runs the {@link #driverLoop()} and the {@link #manipulatorLoop()}
     */
    @Override
    public void loop() {
        driverLoop();
        manipulatorLoop();
    }
}
