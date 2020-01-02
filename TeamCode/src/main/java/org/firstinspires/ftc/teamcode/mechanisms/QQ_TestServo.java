package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class QQ_TestServo extends QQ_Test {
    private double offLocation;
    private double onLocation;
    private Servo servo;
    /**
     * @param description string that describes the test
     * @param offLocation location to move too when off
     * @param onLocation location to move too when on
     * @param servo servo to use during test
     */
    QQ_TestServo(String description, double offLocation, double onLocation, Servo servo) {
        super(description);
        this.offLocation = offLocation;
        this.onLocation = onLocation;
        this.servo = servo;
    }


    /**
     * @param on determines which position the servo moves to
     * @param telemetry allows sending back the position
     */
    @Override
    void run(boolean on, Telemetry telemetry) {
        if (on){
            servo.setPosition(onLocation);
        } else {
            servo.setPosition(offLocation);
        }
        telemetry.addData("Location", servo.getPosition());
    }
}
