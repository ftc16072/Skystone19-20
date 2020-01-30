package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;

public class Parker {
    private static final double SPEED_OUT = -1;
    private static final double SPEED_IN = 0.4;
    public static double FRONT = 1;
    public static double BACK = 1;
    private DcMotor parker;
    private Servo aimer;

    /**
     * initializes the parker
     *
     * @param hwMap hardware map from the config
     */
    void init(HardwareMap hwMap) {
        parker = hwMap.get(DcMotor.class, "parker");
        aimer = hwMap.get(Servo.class, "aimer");
        parker.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * gets test for test wiring
     *
     * @return returns a test that extends the parker
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestMotor("Parker-Out", SPEED_OUT, parker),
                new QQ_TestMotor("Parker-IN", SPEED_IN, parker),
                new QQ_TestServo("aimer", FRONT, BACK, aimer)
        );
    }

    /**
     * extends parker
     */
    public void out() {
        parker.setPower(SPEED_OUT);
    }

    public void in() {
        parker.setPower(SPEED_IN);
    }

    /**
     * stops the parker
     */
    public void stop() {
        parker.setPower(0.0);
    }

    public void aim(boolean front){
        if (front){
            aimer.setPosition(FRONT);
        }else{
            aimer.setPosition(BACK);
        }
    }
}
