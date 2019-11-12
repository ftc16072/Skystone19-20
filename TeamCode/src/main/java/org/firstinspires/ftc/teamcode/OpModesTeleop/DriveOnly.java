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
    private static double MAX_SPEED = 0.8;
    private Robot robot = new Robot();
    private boolean pincerOpen = false;
    private boolean xPressed = false;
    private boolean snatcherOpen = false;
    private boolean bPressed = false;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);

    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        double forward = gamepad1.left_stick_y * -1; //The y direction on the gamepad is reversed idk why
        double strafe = gamepad1.left_stick_x;
        //squaring driving joystick to make it less sensitive in the middle
        forward = forward * forward * Math.signum(forward);
        strafe = strafe * strafe * Math.signum(strafe);
         // unless turbo bumper is pressed, scale speed down
        if(!(gamepad1.right_bumper || gamepad1.left_bumper)){
            forward = MAX_SPEED * forward;
            strafe = MAX_SPEED * strafe;
        }
        Polar g1RightJoystick = Polar.fromCartesian(gamepad1.right_stick_x, -gamepad1.right_stick_y);
        Polar g2RightJoystick = Polar.fromCartesian(gamepad2.right_stick_x, -gamepad2.right_stick_y);
        telemetry.addData("Gyro Heading", robot.getHeadingRadians());

        double r = g1RightJoystick.getR();
        telemetry.addData("r", r);
        if (gamepad1.right_trigger >= 0.05) {
            robot.strafe(gamepad1.right_trigger);
        } else if (gamepad1.left_trigger >= 0.05) {
            robot.strafe(-gamepad1.left_trigger);
        } else {
            if (r >= 0.8) {
                telemetry.addData("joystick angle", g1RightJoystick.getDegrees());
                robot.driveFieldRelativeAngle(strafe, forward, g1RightJoystick.getTheta());
            } else {
                robot.driveFieldRelative(strafe, forward, 0.0);
            }

            if (gamepad1.a) {
                robot.quack();
            }
        }
        if (gamepad2.x && !xPressed){
            pincerOpen = !pincerOpen;
        }
        xPressed = gamepad2.x;

        if (pincerOpen) {
            robot.openPincer();
        } else {
            robot.closePincer();
        }
        if (g2RightJoystick.getR() >= 0.8){
            telemetry.addData("Joystick Angle: ", g2RightJoystick.getDegrees());
            robot.setRotator(g2RightJoystick.getTheta(), AngleUnit.RADIANS, telemetry);
        }
        if (gamepad2.dpad_right){
            robot.setFlipper(Robot.FlipperPositions.UP);
        } else if(gamepad2.dpad_left){
            robot.setFlipper(Robot.FlipperPositions.DOWN);
        } else {
            robot.setFlipper(Robot.FlipperPositions.STOP);
        }
        if(gamepad2.right_bumper || gamepad2.left_bumper){
            if (gamepad2.dpad_up){
                robot.moveLifter(0.7);

            } else if (gamepad2.dpad_down) {
                robot.moveLifter(-0.7);
            } else {
                robot.moveLifter(0);
            }

        }else{
            if (gamepad2.dpad_up) {
                robot.moveLifter(0.5);

            } else if (gamepad2.dpad_down){
                robot.moveLifter(-0.5);
            } else {
                robot.moveLifter(0);
            }

        }
        if (gamepad2.b && !bPressed){
            snatcherOpen = !snatcherOpen;
        }
        bPressed = gamepad2.b;

        if (snatcherOpen) {
            robot.liftSnatcher();
        } else {
            robot.lowerSnatcher();
        }
    }
}
