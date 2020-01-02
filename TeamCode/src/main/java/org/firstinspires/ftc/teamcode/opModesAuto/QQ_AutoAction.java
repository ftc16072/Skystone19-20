package org.firstinspires.ftc.teamcode.opModesAuto;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Robot;

abstract class QQ_AutoAction {
    /**
     * makes sure that all auto actions have a run command with
     * 
     * @param robot gives access to all robot functions
     * @param gameTime lets us know the time since the op-mode was selected
     * @param telemetry lets us print stuff back to the telemetry
     * @return returns true when completed iff it wasn't abstract
     */
    abstract boolean run(Robot robot, double gameTime, Telemetry telemetry);
}
