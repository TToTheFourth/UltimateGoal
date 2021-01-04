package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RepresentoBotMVP;

/**
 * Expected Result: Robot slides right 50 inches
 */
@Autonomous(group = "Tests")
public class SlideRightInchesTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RepresentoBotMVP bot = new RepresentoBotMVP(this);
        bot.startGyro();

        waitForStart();

        bot.slide(0.5, 50);
    }
}
