package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.UltimateVuforia;
import org.firstinspires.ftc.teamcode.VuforiaNavigator;

@Autonomous
public class TestCaseBoxOne extends LinearOpMode {
    RepresentoBotMVP bot;
    UltimateVuforia vu;
    VuforiaNavigator vuNav;
//    int rings;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        vu = new UltimateVuforia(this);
        vuNav = new VuforiaNavigator(this, bot, vu);
        bot.startGyro();
        vu.yesVuforia();

        waitForStart();

//        rings = vu.tensorflow();
//        telemetry.addData("rings", rings);
//        telemetry.update();
//        if (rings == 1) {
        bot.clawClosePosition();
        bot.dropSweep();

        bot.goForwardGyroErrorCorrection(0.5, 53);
        bot.shootRings(3);

        bot.goForwardGyroErrorCorrection(0.5, 59);
        bot.clawOpenPosition();
        bot.slide(0.5, 12);
        bot.goForwardGyroErrorCorrection(-0.5, 37);
//        sleep(2000);
//        vuNav.navigate(36, 36, 0);
//        sleep(2000);
//        vuNav.navigate(36, 36, 0);
//        bot.goForwardGyroErrorCorrection(-.5, 40);
//        bot.clawClosePosition();
//        }
        vu.noVuforia();
    }
}
