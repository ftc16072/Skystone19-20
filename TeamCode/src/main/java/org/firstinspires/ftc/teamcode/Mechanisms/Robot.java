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
    private Pincer pincer = new Pincer();
    private Rotator rotator = new Rotator();
    public Flipper flipper = new Flipper();
    public Lifter lifter = new Lifter();
    private Snatcher snatcher = new Snatcher();
    public Parker parker = new Parker();
    public Dispenser dispenser = new Dispenser();
    private int quackID;
    private Context appContext;
    private boolean quacking = false;
    public RobotLights robotLights = new RobotLights();

    public enum FlipperPositions {
        UP,
        DOWN,
        INIT,
        STOP
    }

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

    /**
     * sets flipper to position
     * @param position what Flipper position to use [UP, DOWN, STOP]
     */
    public void setFlipper(FlipperPositions position){
        switch (position){
            case UP:
                flipper.up();
                break;
            case DOWN:
                flipper.down();
                break;
            case STOP:
                flipper.stop();
        }
    }

    public void openPincer(){
        pincer.open();
    }
    public void closePincer(){
        pincer.close();
    }
    public void liftSnatcher(){
        snatcher.lift();
    }
    public void lowerSnatcher(){
        snatcher.lower();
    }
    public void setRotator(double degrees, AngleUnit angleUnit, Telemetry telemetry){
        rotator.rotate(degrees, angleUnit, telemetry);
    } 
    public void moveLifter(double speed) {
        lifter.move(speed);
    }
    

}
