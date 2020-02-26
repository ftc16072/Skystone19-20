package org.firstinspires.ftc.teamcode.opModesAuto;

import android.support.annotation.RequiresFeature;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.actions.QQ_ActionDelayFor;
import org.firstinspires.ftc.teamcode.actions.QQ_ActionPincer;
import org.firstinspires.ftc.teamcode.actions.QQ_AutoAction;

import java.util.Arrays;
import java.util.List;



@Autonomous(name="Servo Test")
public class servoTestAuto extends AutoBase {
    @Override
    List<QQ_AutoAction> getSteps() {
        return Arrays.asList(
                new QQ_ActionPincer(true),
                new QQ_ActionDelayFor(1.25)
        );
    }
}
