package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RepresentoBotMVP;

@Autonomous
public class TestCaseWobbleGoal extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RepresentoBotMVP bot = new RepresentoBotMVP(this);
        bot.startGyro();
        waitForStart();
        bot.clawClosePosition();
        sleep(3000);
        bot.clawOpenPosition();
        sleep(6000);
    }
}
