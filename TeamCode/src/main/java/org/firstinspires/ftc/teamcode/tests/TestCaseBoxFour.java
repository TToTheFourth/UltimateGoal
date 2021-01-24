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
            bot.goForward(0.5, 10);
            bot.slide(-0.5, 19);
            bot.goForward(0.5, 102);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 18);
            vu.getCoords();
            vuNav.navigate(60, 60, 0);
            sleep(500);
            vuNav.navigate(60, 60, 0);
            bot.clawOpenPosition();
            //
//        }
        vu.noVuforia();
    }
}
