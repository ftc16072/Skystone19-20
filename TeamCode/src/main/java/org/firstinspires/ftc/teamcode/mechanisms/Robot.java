package org.firstinspires.ftc.teamcode.mechanisms;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    public final Navigation nav = new Navigation();
    public final Pincer pincer = new Pincer();
    public final Rotator rotator = new Rotator();
    public final Flipper flipper = new Flipper();
    public final Lifter lifter = new Lifter();
    public final Snatcher snatcher = new Snatcher();
    public final Parker parker = new Parker();
    public final Dispenser dispenser = new Dispenser();
    private int quackID;
    private Context appContext;
    private boolean quacking = false;
    public final RobotLights robotLights = new RobotLights();


    /**
     * initializes the robot and all mechanisms on it
     *
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
