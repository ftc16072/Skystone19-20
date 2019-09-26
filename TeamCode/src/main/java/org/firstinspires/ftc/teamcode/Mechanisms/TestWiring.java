package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class TestWiring extends OpMode {
    private MecanumDrive mecanumDrive = new MecanumDrive();

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        mecanumDrive.init(hardwareMap);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        telemetry.addLine("Bumpers = front, triggers = back");
        if (gamepad1.left_bumper) {
            mecanumDrive.setSpeeds(1, 0, 0, 0);

        } else if (gamepad1.right_bumper) {
            mecanumDrive.setSpeeds(0, 1, 0, 0);
        } else if (gamepad1.left_trigger > 0.0) {
            mecanumDrive.setSpeeds(0, 0, 1, 0);

        } else if (gamepad1.right_trigger > 0.0) {
            mecanumDrive.setSpeeds(0, 0, 0, 1);

        } else {
            mecanumDrive.setSpeeds(0, 0, 0, 0);
        }
        mecanumDrive.reportEncoders(telemetry);
    }
 }
