package org.firstinspires.ftc.teamcode.Mechanisms;

import org.firstinspires.ftc.robotcore.external.Telemetry;

abstract class QQ_Test {
    private String description;

    /**
     * abstract constructer to make sure every test has a description
     * @param description the string description for the test
     */
    QQ_Test(String description) {
        this.description = description;
    }

    /**
     * @return returns the description
     */
    String getDescription() {
        return description;
    }

    /**
     * abstract method to make sure all the tests have a run method
     * @param on this tells wether run or just display the description
     * @param telemetry this gives all the runs access to the telemetry so it can print statements out
     */
    abstract void run(boolean on, Telemetry telemetry);

}
