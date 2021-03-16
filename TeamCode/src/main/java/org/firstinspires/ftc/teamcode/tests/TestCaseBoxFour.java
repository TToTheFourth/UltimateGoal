package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.UltimateVuforia;
import org.firstinspires.ftc.teamcode.VuforiaNavigator;

@Autonomous
public class TestCaseBoxFour extends LinearOpMode {
    RepresentoBotMVP bot;
    UltimateVuforia vu;
    VuforiaNavigator vuNav;
//    int rings;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        vu = new UltimateVuforia(this);
        vuNav = new VuforiaNavigator(this, bot, vu);
        waitForStart();
        bot.startGyro();
        vu.yesVuforia();

//        rings = vu.tensorflow();
//        telemetry.addData("rings", rings);
//        telemetry.update();
//        if (rings == 4) {
        bot.clawClosePosition();
        bot.dropSweep();

        bot.goForwardGyroErrorCorrection(0.5, 53);
        bot.shootRings(3);

        bot.goForwardGyroErrorCorrection(0.5, 63);
        bot.slide(-0.5, 12);
        //change 12 -> 10
        bot.turnRight(83, 0.3);
        bot.goForwardGyroErrorCorrection(-0.5, 8);
        bot.clawOpenPosition();
        bot.goForwardGyroErrorCorrection(0.5, 10);
        bot.turnRight(90, 0.3);
        bot.goForwardGyroErrorCorrection(0.5, 50);
//        }


        vu.noVuforia();
    }
}
