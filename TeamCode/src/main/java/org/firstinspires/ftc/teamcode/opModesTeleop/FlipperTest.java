package org.firstinspires.ftc.teamcode.opModesTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Robot;

@TeleOp(name = "Flipper Test")
public class FlipperTest extends OpMode {
    private final Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            robot.flipper.goToDegree(90, telemetry);
        } else if(gamepad1.b){
            robot.flipper.goToDegree(45, telemetry);
        } else if (gamepad1.y){
            robot.flipper.goToDegree(-10, telemetry);
        } else{
            robot.flipper.stop();
        }
    }
}
