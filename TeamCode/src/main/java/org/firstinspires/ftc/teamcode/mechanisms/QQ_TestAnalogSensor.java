package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class QQ_TestAnalogSensor extends QQ_Test {
    private AnalogInput analogInput;

    /**
     * @param description string that describes the test
     * @param analogInput sensor to use during test
     */
    QQ_TestAnalogSensor(String description, AnalogInput analogInput) {
        super(description);
        this.analogInput = analogInput;
    }


    /**
     * determines whether to move the servo to the on location of the off location
     *
     * @param on        determines which position the servo moves to
     * @param telemetry allows sending back the position
     */
    @Override
    void run(boolean on, Telemetry telemetry) {
        telemetry.addData("Voltage", analogInput.getVoltage());
    }
}
