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
    private LynxEmbeddedIMU imu;
    private MecanumDrive mecanumDrive = new MecanumDrive();
    private Pincer pincer = new Pincer();
    private Rotator rotator = new Rotator();
    private int quackID;
    private Context appContext;
    private boolean quacking = false;

    public void init(HardwareMap hwMap) {
        imu = hwMap.get(LynxEmbeddedIMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        imu.initialize(params);
        mecanumDrive.init(hwMap);
        pincer.init(hwMap);
        rotator.init(hwMap);
        appContext = hwMap.appContext;
        quackID = appContext.getResources().getIdentifier("quack", "raw", hwMap.appContext.getPackageName());
        quacking = false;
    }

    public double getHeadingRadians() {
        Orientation angles;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return -angles.firstAngle;   // Not sure why this is negative, but philip guessed it :)

    }

    public void driveFieldRelative(double x, double y, double rotate) {
        Polar drive = Polar.fromCartesian(x, y);
        double heading = getHeadingRadians();

        drive.subtractAngle(heading);
        mecanumDrive.driveMecanum(drive.getY(), drive.getX(), rotate);
    }

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

    public void strafe(double speed) {
        mecanumDrive.driveMecanum(0, speed, 0);
    }

    public void driveFieldRelativeAngle(double x, double y, double angle) {
        double delta = angle - getHeadingRadians();
        if (delta >= Math.PI) {
            delta = delta - (2 * Math.PI);
        } else if (delta <= -Math.PI) {
            delta = delta + (2 * Math.PI);
        }
        double MAX_ROTATE = 0.7; //This is to shrink how fast we can rotate so we don't fly past the angle
        delta = Range.clip(delta, -MAX_ROTATE, MAX_ROTATE);
        driveFieldRelative(x, y, delta);
    }

    public void openPincer(){
        pincer.open();
    }
    public void closePincer(){
        pincer.close();
    }
    public void setRotator(double degrees, AngleUnit angleUnit, Telemetry telemetry){
        rotator.rotate(degrees, angleUnit, telemetry);
    }


}
