package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

class QQ_TestDistanceSensor extends QQ_Test {
    private DistanceSensor distanceSensor;
    
    /**
     * Constructor for the test
     * @param description string describing the test
     * @param distanceSensor the distance sensor that will be used in the test
     */
    QQ_TestDistanceSensor(String description, DistanceSensor distanceSensor) {
        super(description);
        this.distanceSensor = distanceSensor;
    }

    /**
     * prints the distance in in or cm to the telemetry
     * @param on determines if it's in or cm True = in; false = cm
     * @param telemetry allows for the distance to be sent back
     */
    @Override
    void run(boolean on, Telemetry telemetry) {
        if (on) {
            telemetry.addData("Distance (in):", distanceSensor.getDistance(DistanceUnit.INCH));
        } else {
            telemetry.addData("Distance (cm):", distanceSensor.getDistance(DistanceUnit.CM));
        }
    }
}
