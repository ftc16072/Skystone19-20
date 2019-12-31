package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import android.graphics.Color;
import android.support.annotation.ColorInt;

public class RobotLights {
    private QwiicLEDStrip leds;
    private ElapsedTime elapsedTime = new ElapsedTime();
    private int red_color;
    private int blue_color;
    private int green_color;
    private Random rand = new Random();

    /**
     * initilizes the lights
     * @param hwmap hardware map from config
     */
    void init(HardwareMap hwmap) {
        leds = hwmap.get(QwiicLEDStrip.class, "robot_lights");
        //       leds.setBrightness(5);
        leds.setColor(Color.rgb(0, 0, 0));
    }
    /**
     * @return list of tests 
     */
    List<QQ_Test> getTests() {
        return Arrays.asList(
                new QQ_TestLights("tests the led strip",leds)
        );
    }

    /**
     * Sets the color of the strip
     * @param color what color to set it to as a color int
     * @see ColorInt
     */
    public void setColor(@ColorInt int color) {
        leds.setColor(color);
    }

    /**
     * sets the alliance lights
     * @param isBlue is the alliance blue
     */
    public void allianceLights(boolean isBlue) {
        if (isBlue) {
            leds.setColor(Color.rgb(0, 0, 255));
        } else {
            leds.setColor(Color.rgb(255, 0, 0));
        }
    }
}