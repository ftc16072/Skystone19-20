package org.firstinspires.ftc.teamcode.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;


@Config
public class Flipper {
    private static final double SPEED_UP = -0.9;
    private static final double SPEED_DOWN = 0.4;
    private DcMotor flipper;
    private AnalogInput flipperSensor;
    private static final double VOLTAGE_DOWN = 1.272;
    private static final double VOLTAGE_UP = 0.435;
    private static final double DEGREES_DOWN = -10;
    private static final double DEGREES_UP = 90;
    private static final double VOLTAGE_TOLERANCE = 0.05;
    public static double KP_VOLTAGE_UP = 1.8;
    public static double KP_VOLTAGE_DOWN = 0.3;


    double getDesiredVoltage(double desiredAngle){
        return VOLTAGE_UP + (((desiredAngle - DEGREES_UP) * (VOLTAGE_DOWN - VOLTAGE_UP))/(DEGREES_DOWN-DEGREES_UP)); //From Rosetta code
    }

    /**
     * This initializes our Flipper.
     * it makes sure that it will break at 0
     * it then makes sure it won't move much
     *
     * @param hwMap This is the Hardware map from the configuration
     */
    void init(HardwareMap hwMap) {
        flipper = hwMap.get(DcMotor.class, "flipper");
        flipperSensor = hwMap.get(AnalogInput.class, "flipperSensor");
        flipper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flipper.setPower(0.0);
    }

    /**
     * This gives us flipper tests for test wiring
     *
     * @return a flipper up and flipper down test for test wiring
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestMotor("Flipper - Up", SPEED_UP, flipper),
                new QQ_TestMotor("Flipper - Down", SPEED_DOWN, flipper),
                new QQ_TestAnalogSensor("Flipper Sensor", flipperSensor)
        );
    }

    /**
     * Moves the flipper down
     */
    public void down() {
        flipper.setPower(SPEED_DOWN);
    }

    /**
     * Moves the flipper up
     */
    public void up() {
        flipper.setPower(SPEED_UP);
    }

    /**
     * Stops the flipper
     */
    public void stop() {
        flipper.setPower(0.0);
    }

    /**
     * Keeps the flipper held against the top of the lift for autos
     */
    public void holdUp() {
        flipper.setPower(-0.2);
    }

    /**
     * gets encoder ticks of flipper
     * @return encoder position of flipper
     */
    public double getPosition() {
        return flipper.getCurrentPosition();
    }

    private boolean goToVoltage(double desiredVoltage, Telemetry telemetry){
        double voltageDiff = flipperSensor.getVoltage() - desiredVoltage;
        if (Math.abs(voltageDiff) <= VOLTAGE_TOLERANCE){
            flipper.setPower(0.0);
            return true;
        }
        telemetry.addData("Current Voltage", flipperSensor.getVoltage());
        telemetry.addData("Desired Voltage", desiredVoltage);
        telemetry.addData("Voltage Diff", voltageDiff);

        if (voltageDiff > 0){
            flipper.setPower(KP_VOLTAGE_DOWN * voltageDiff);
            telemetry.addData("power set", KP_VOLTAGE_DOWN * voltageDiff);
        } else {
            flipper.setPower(KP_VOLTAGE_UP * voltageDiff);
            telemetry.addData("power set", KP_VOLTAGE_UP * voltageDiff);
        }
        return false;
    }

    public boolean goToDegree(double desiredDegree, Telemetry telemetry){
        return goToVoltage(getDesiredVoltage(desiredDegree), telemetry);
    }



}

