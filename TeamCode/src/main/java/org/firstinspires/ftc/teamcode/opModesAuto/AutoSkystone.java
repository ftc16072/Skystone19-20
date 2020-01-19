package org.firstinspires.ftc.teamcode.opModesAuto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Autonomous(name = "Skystone", group = "ftc16072")
public class AutoSkystone extends AutoBase {


    /**
     * overrides to Force placement in the depot area
     *
     * @see AutoBase
     */
    @Override
    public void init_loop() {
        super.init_loop();
        startDepot = true;
    }

    /**
     * overrides to make sure the flipper stays up
     */
    @Override
    public void start() {
        super.start();
        robot.flipper.holdUp();
    }

    /**
     * @return list of steps based on alliance
     */
    List<QQ_AutoAction> getSteps() {
        List<QQ_AutoAction> steps = new ArrayList<>();
        QQ_ActionSetPosition startPosition =
                new QQ_ActionSetPosition(getStartPosition());
        if (redAlliance) {
            steps.addAll(Arrays.asList(
                    startPosition,
                    new QQ_ActionRotator(0.0),
                    new QQ_ActionPincer(false),
                    new QQ_ActionDriveTo(48, -36, DistanceUnit.INCH),
                    //new QQ_ActionFindSkystone(vuforia, redAlliance),
                    new QQ_ActionDriveToRelative(-12, 0, DistanceUnit.INCH),
                    new QQ_ActionFlipper(1),
                    new QQ_ActionPincer(true),
                    new QQ_ActionDelayFor(1)


            ));
        } else {
            steps.addAll(Arrays.asList(
                    startPosition
                    //new QQ_ActionFindSkystone(vuforia, redAlliance)

            ));
        }
        //steps.addAll(getParkSteps());
        return steps;
    }

    /**
     * simplifies {@link #getSteps()} by moving parking commands out
     *
     * @return commands to park based on weather we are parking near or far
     */
    private List<QQ_AutoAction> getParkSteps() {
        if (farPark) {
            return Arrays.asList(
                    new QQ_ActionDriveTo(farPark_x, 0, DistanceUnit.INCH));
        } else {
            return Arrays.asList(
                    new QQ_ActionDriveTo(nearPark_x, 0, DistanceUnit.INCH));
        }
    }
}