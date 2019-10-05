package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@TeleOp()
public class TestWiring extends OpMode {
    private MecanumDrive mecanumDrive = new MecanumDrive();
    private Pincer pincer = new Pincer();
    private Rotator rotator = new Rotator();
    private List<QQ_Test> tests;
    private boolean wasDown, wasUp;
    private int testNum;


    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        pincer.init(hardwareMap);
        mecanumDrive.init(hardwareMap);
        rotator.init(hardwareMap);
        tests = new ArrayList<>();
        tests.addAll(mecanumDrive.getTests());
        tests.addAll(rotator.getTests());
                                                                                                                                                                                         tests.addAll(pincer.getTests());
        // To add more tests here, do this: tests.addAll(sampleMechanism.getTests());
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        if (gamepad1.dpad_up && !wasUp) {
            testNum--;
            if (testNum < 0) {
                testNum = tests.size() - 1;
            }
        }
        wasUp = gamepad1.dpad_up;
        if (gamepad1.dpad_down && !wasDown){
            testNum++;
            if (testNum >= tests.size()) {
                testNum = 0;
            }
        }
        wasDown = gamepad1.dpad_down;

        telemetry.addLine("Use Up and Down on D-pad to cycle through choices");
        telemetry.addLine("Press A to run test");
        QQ_Test currTest = tests.get(testNum);
        telemetry.addData("Test:", currTest.getDescription());
        currTest.run(gamepad1.a, telemetry);
     }
 }
