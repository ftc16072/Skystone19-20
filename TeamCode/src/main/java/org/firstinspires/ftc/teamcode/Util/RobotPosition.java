package org.firstinspires.ftc.teamcode.Util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RobotPosition{
    private double x_cm;
    private double y_cm;
    private double heading_radians;
    /**
     * @param x x location
     * @param y y location
     * @param du distance unit x and y are in
     * @param heading current heading
     * @param au angle unit heading is in
     */
    public RobotPosition(double x, double y, DistanceUnit du, double heading, AngleUnit au){
        x_cm = du.toCm(x);
        y_cm = du.toCm(y);
        heading_radians = au.toRadians(heading);
    }

    /**
     * @param du what distance unit do you want it in
     * @return x in the requested distance unit
     */
    public double getX(DistanceUnit du){
        return du.fromCm(x_cm);
    }
    
    /**
     * @param du what distance unit do you want it in
     * @return y in the requested distance unit
     */
    public double getY(DistanceUnit du){
        return du.fromCm(y_cm);
    }
    
    /**
     * @param au what angle unit do you want it in
     * @return the heading in the requested angle unit
     */
    public double getHeading(AngleUnit au){
        return au.fromRadians(heading_radians);
    }
}