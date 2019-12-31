package org.firstinspires.ftc.teamcode.Util;

public class Polar {

    private double theta;
    private double r;
    /**
     * Constructor based on polar coordinates
     * @param theta theta or angle from x-axis to point
     * @param r r or distance from (0,0) to point
     */
    Polar(double theta, double r) {
        this.theta = theta;
        this.r = r;
    }

    /**
     * @return returns theta in radians
     */
    public double getTheta() {
        return theta;
    }

    /**
     * @return thata in degrees 
     */
    public double getDegrees() {
        return theta * 360 / (2 * Math.PI);
    }

    /**
     * @return r
     */
    public double getR() {
        return r;
    }

    /**
     * creates a polar class from cartesian coordinates
     * @param x x in cartesian coordinates
     * @param y y in cartesian corrdinates
     * @return instance of Polar class having translated to polar coordinates
     */
    public static Polar fromCartesian(double x, double y) {
        double r = Math.hypot(x, y);
        double theta = Math.atan2(y, x);
        return new Polar(theta, r);
    }

    /**
     * subtracts heading from theta
     * @param heading what to subtract (in radians)
     */
    public void subtractAngle(double heading) {
        theta = theta - heading;
    }

    /**
     * @return x in cartesian coordinates
     */
    public double getX() {
        return r * Math.cos(theta);
    }

    /**
     * @return y in cartesian coordinates
     */
    public double getY() {
        return r * Math.sin(theta);
    }


}