package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Util.Vuforia;
import org.firstinspires.ftc.teamcode.Util.Vuforia;

@Autonomous()
//@Disabled
public class LookForSkystone extends OpMode {
    private Robot robot = new Robot();
    private Vuforia vuforia = new Vuforia();

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);
        vuforia.start(hardwareMap);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        double distance = vuforia.whereIsSkystone(telemetry);
        if (distance != -2000) {
            double speed = (distance * 0.008);
            robot.strafe(speed);
            telemetry.addData("Traveling", speed);
        } else {
            robot.strafe(0);
            telemetry.addData("Traveling", "Not Found");
        }

        //robot.strafe(0.3);
    }
}
