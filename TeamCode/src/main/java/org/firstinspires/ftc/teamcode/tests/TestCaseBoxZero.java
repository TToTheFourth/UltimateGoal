package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.UltimateVuforia;
import org.firstinspires.ftc.teamcode.VuforiaNavigator;
import org.tensorflow.lite.TensorFlowLite;

@Autonomous
public class TestCaseBoxZero extends LinearOpMode {
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
//        if (rings == 0) {
        bot.clawClosePosition();
        bot.dropSweep();

        bot.goForwardGyroErrorCorrection(0.5, 53);
        bot.shootRings(3);

        bot.slide(-0.5, 12);
        bot.goForwardGyroErrorCorrection(0.5, 28);
        bot.clawOpenPosition();
        bot.slide(0.5, 18);
        bot.goForwardGyroErrorCorrection(-0.5, 12);
//        }
        vu.noVuforia();
    }
}
