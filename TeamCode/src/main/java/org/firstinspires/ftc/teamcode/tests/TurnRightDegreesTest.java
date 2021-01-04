package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RepresentoBotMVP;

/**
 * Expected Result: Robot turns right 180 degrees
 */
@Autonomous(group = "Tests")
public class TurnRightDegreesTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RepresentoBotMVP bot = new RepresentoBotMVP(this);
        bot.startGyro();

        waitForStart();

        bot.turnRight(180, 0.3);
    }
}