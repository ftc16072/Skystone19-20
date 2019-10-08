package org.firstinspires.ftc.teamcode.OpModesTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Util.Polar;

@TeleOp()
public class DriveOnly extends OpMode {
    private Robot robot = new Robot();
    private boolean pincerOpen = false;
    private boolean xPresed = false;

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
        Polar g1RightJoystick = Polar.fromCartesian(gamepad1.right_stick_x, -gamepad1.right_stick_y);
        Polar g2LeftJoystick = Polar.fromCartesian(gamepad2.right_stick_x, -gamepad2.right_stick_y);
        telemetry.addData("Gyro Heeading", robot.getHeadingRadians());


        double r = g1RightJoystick.getR();
        telemetry.addData("r", r);
        if (gamepad1.right_trigger >= 0.05) {
            robot.strafe(gamepad1.right_trigger);
        } else if (gamepad1.left_trigger >= 0.05) {
            robot.strafe(-gamepad1.left_trigger);
        } else if (gamepad1.right_bumper) {
            robot.driveFieldRelative(0, 0, 0.2);
        } else if (gamepad1.left_bumper) {
            robot.driveFieldRelative(0, 0, -0.2);
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
        if (gamepad2.x && !xPresed){
            pincerOpen = !pincerOpen;
            xPresed = true;
        }
        if (pincerOpen) {
            robot.openPincer();
            xPresed = false;
        } else {
            robot.closePincer();
            xPresed = false;
        }
        if (g2LeftJoystick.getR() >= 0.8){
            robot.setRotator(g2LeftJoystick.getTheta(), AngleUnit.RADIANS);
        }
    }
}
