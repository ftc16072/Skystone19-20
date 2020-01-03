package org.firstinspires.ftc.teamcode.opModesAuto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;
import org.firstinspires.ftc.teamcode.util.Vuforia;

import java.util.List;

public class QQ_ActionFindSkystone extends QQ_AutoAction {
    Vuforia vuforia;
    double strafeSpeed;

    QQ_ActionFindSkystone(Vuforia vuforia, Boolean redAlliance) {
        this.vuforia = vuforia;
        if (redAlliance) {
            strafeSpeed = 0.2;
        } else {
            strafeSpeed = -0.2;
        }

    }

    @Override
    boolean run(Robot robot, double gameTime, Telemetry telemetry) {
        double distanceX = vuforia.whereIsSkystone(telemetry);
        if (distanceX != -2000) {
            if (distanceX <= 2) {
                robot.nav.strafe(0.0);
                return true;
            }
            double speed = (distanceX * 0.008);
            robot.nav.strafe(speed);
            telemetry.addData("Traveling", speed);
            return false;
        } else {
            robot.nav.strafe(strafeSpeed);
            telemetry.addData("Traveling", "Not Found");
            return false;
        }
    }

}
