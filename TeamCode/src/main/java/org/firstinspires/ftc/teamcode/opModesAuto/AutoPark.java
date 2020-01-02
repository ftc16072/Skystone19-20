package org.firstinspires.ftc.teamcode.opModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Arrays;
import java.util.List;

@Autonomous(name = "Park", group = "ftc16072")
public class AutoPark extends AutoBase {
    /**
     * main body of Auto
     *
     * @return returns the steps for auto based on starting location
     */
    List<QQ_AutoAction> getSteps() {
        QQ_ActionSetPosition startPosition =
                new QQ_ActionSetPosition(getStartPosition());

        if (farPark) {
            if (startDepot) {
                return Arrays.asList(
                        startPosition,
                        new QQ_ActionDriveTo(farPark_x, START_DEPOT_Y, DistanceUnit.INCH),
                        new QQ_ActionDriveTo(farPark_x, 0, DistanceUnit.INCH));
            } else {
                return Arrays.asList(
                        startPosition,
                        new QQ_ActionDriveTo(farPark_x, START_BUILD_Y, DistanceUnit.INCH),
                        new QQ_ActionDriveTo(farPark_x, 0, DistanceUnit.INCH));
            }
        } else {
            return Arrays.asList(
                    startPosition,
                    new QQ_ActionDriveTo(nearPark_x, 0, DistanceUnit.INCH));
        }
    }
}