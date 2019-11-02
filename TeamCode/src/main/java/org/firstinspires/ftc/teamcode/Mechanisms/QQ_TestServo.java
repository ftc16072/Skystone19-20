package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class QQ_TestServo extends QQ_Test {
    private double offLocation;
    private double onLocation;
    private Servo servo;
    QQ_TestServo(String description, double offLocation, double onLocation, Servo servo) {
        super(description);
        this.offLocation = offLocation;
        this.onLocation = onLocation;
        this.servo = servo;
    }



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
