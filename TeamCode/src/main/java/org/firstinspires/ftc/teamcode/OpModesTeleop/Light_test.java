package org.firstinspires.ftc.teamcode.OpModesTeleop;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.Random;

import org.firstinspires.ftc.teamcode.Mechanisms.QwiicLEDStrip;

@TeleOp()
public class Light_test extends OpMode {
    private QwiicLEDStrip leds;
    private ElapsedTime elapsedTime = new ElapsedTime();
    private int red_color;
    private int green_color;
    private int blue_color;
    Random rand = new Random();
    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        leds = hardwareMap.get(QwiicLEDStrip.class, "Light_Test");
        leds.setBrightness(1);
        leds.setColor(Color.rgb(0, 0, 0));
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        if (elapsedTime.milliseconds() >= 200) {
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(1, Color.rgb(red_color, green_color, blue_color));
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(2, Color.rgb(red_color, green_color, blue_color));
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(3, Color.rgb(red_color, green_color, blue_color));
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(4, Color.rgb(red_color, green_color, blue_color));
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(5, Color.rgb(red_color, green_color, blue_color));
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(6, Color.rgb(red_color, green_color, blue_color));
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(7, Color.rgb(red_color, green_color, blue_color));
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(8, Color.rgb(red_color, green_color, blue_color));
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(9, Color.rgb(red_color, green_color, blue_color));
            red_color = rand.nextInt(256);
            green_color = rand.nextInt(256);
            blue_color = rand.nextInt(256);
            leds.setColor(10, Color.rgb(red_color, green_color, blue_color));
            elapsedTime.reset();
        }

    }
}
