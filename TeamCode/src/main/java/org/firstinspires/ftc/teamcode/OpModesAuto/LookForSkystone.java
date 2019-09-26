package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

@Autonomous()
//@Disabled
public class LookForSkystone extends OpMode {
    private Robot robot = new Robot();

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);

    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        robot.strafe(0.3);
    }
}
