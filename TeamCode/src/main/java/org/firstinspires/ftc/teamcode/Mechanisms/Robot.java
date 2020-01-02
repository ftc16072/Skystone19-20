package org.firstinspires.ftc.teamcode.Mechanisms;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import org.firstinspires.ftc.teamcode.Util.Polar;

public class Robot {
    public Navigation nav = new Navigation();
    public Pincer pincer = new Pincer();
    public Rotator rotator = new Rotator();
    public Flipper flipper = new Flipper();
    public Lifter lifter = new Lifter();
    public Snatcher snatcher = new Snatcher();
    public Parker parker = new Parker();
    public Dispenser dispenser = new Dispenser();
    private int quackID;
    private Context appContext;
    private boolean quacking = false;
    public RobotLights robotLights = new RobotLights();


    /**
     * initializes the robot and all mecanisms on it
     * @param hwMap hardware map from config
     */
    public void init(HardwareMap hwMap) {
        nav.init(hwMap);
        pincer.init(hwMap);
        rotator.init(hwMap);
        flipper.init(hwMap);
        lifter.init(hwMap);
        snatcher.init(hwMap);
        robotLights.init(hwMap);
        parker.init(hwMap);
        dispenser.init(hwMap);
        appContext = hwMap.appContext;
        quackID = appContext.getResources().getIdentifier("quack", "raw", hwMap.appContext.getPackageName());
        quacking = false;
    }

    /**
     * Quacks!!
     */
    public void quack() {
        if (!quacking) {
            // create a sound parameter that holds the desired player parameters.
            SoundPlayer.PlaySoundParams params = new SoundPlayer.PlaySoundParams();
            params.loopControl = 0;
            params.waitForNonLoopingSoundsToFinish = true;

            quacking = true;
            SoundPlayer.getInstance().startPlaying(appContext, quackID, params, null,
                    () -> quacking = false
            );
        }
    }


}
