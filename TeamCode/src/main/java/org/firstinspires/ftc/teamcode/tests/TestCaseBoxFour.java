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
//    UltimateVuforia vu;
//    int rings;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
//        vu = new UltimateVuforia(this);
        waitForStart();
        bot.startGyro();
//        vu.yesVuforia();

//        rings = vu.tensorflow();
//        telemetry.addData("rings", rings);
//        telemetry.update();
//        if (rings == 4) {
            bot.turnLeft(90, 0.3);
            bot.goForward(0.5, 24);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 115);
//        }
//        vu.noVuforia();
    }
}
