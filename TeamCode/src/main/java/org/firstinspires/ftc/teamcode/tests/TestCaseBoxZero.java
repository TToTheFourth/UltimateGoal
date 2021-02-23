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
        waitForStart();
        bot.startGyro();
        vu.yesVuforia();

//        rings = vu.tensorflow();
//        telemetry.addData("rings", rings);
//        telemetry.update();
//        if (rings == 0) {
            bot.clawClosePosition();
            bot.goForwardGyroErrorCorrection(0.5, 5);
            bot.turnLeft(85, 0.3);
            bot.goForward(0.5, 25);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 68);
            sleep(500);
            vuNav.navigate(14, 60, 0);
            sleep(500);
            vuNav.navigate(14, 60, 0);
            bot.clawOpenPosition();
//        }
        vu.noVuforia();
    }
}
