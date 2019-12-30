package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Util.RobotPosition;

import java.util.Arrays;
import java.util.List;

@Autonomous(name = "Park", group = "ftc16072")
public class AutoPark extends QQ_AutoBase {
    /**
     * main body of Auto
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