package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.UltimateVuforia;

@Autonomous
public class TestForTensorFlowWithMovement extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        UltimateVuforia nulvin = new UltimateVuforia(this);
        RepresentoBotMVP bot = new RepresentoBotMVP(this);
        VuforiaNavigator vuNav = new VuforiaNavigator(this, bot, nulvin);

        nulvin.yesVuforia();
        bot.startGyro();

        waitForStart();

        int rings;
        float zeroRing=0;
        float oneRing=0;
        float fourRing=0;
        float conf = 1;

        int sampleCount = 0;
        while(sampleCount < 10) {
            RingResult result = nulvin.getRings();
            rings = result.getRingCount();
            conf = result.getConfidence();

            if (rings >= 0) {
                sampleCount++;

                if (rings == 0) {
                    zeroRing = zeroRing + 1;
                } else if (rings == 1) {
                    oneRing = oneRing + 1;
                } else if (rings == 4) {
                    fourRing = fourRing + 1;
                }
            } else {
                idle();
            }
        }

        telemetry.addData("Zero", zeroRing);
        telemetry.addData("One", oneRing);
        telemetry.addData("Four", fourRing);

        telemetry.update();

        if (fourRing > oneRing & fourRing > zeroRing) {
            //TensorFlow saw four rings the most
            bot.clawClosePosition();
            bot.goForward(0.5, 10);
            bot.slide(-0.5, 19);
            bot.goForward(0.5, 102);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 18);
            //nulvin.getCoords();
            //vuNav.navigate(60, 60, 0);
            //sleep(500);
            //vuNav.navigate(60, 60, 0);
            //bot.clawOpenPosition();
        } else if (oneRing > fourRing & oneRing > zeroRing) {
            //TensorFlow saw one ring the most
            bot.clawOpenPosition();
            bot.goForward(0.5, 112);
            //sleep(2000);
            //vuNav.navigate(36, 36, 0);
            //sleep(2000);
            //vuNav.navigate(36, 36, 0);
            //bot.clawOpenPosition();
        } else if (zeroRing > fourRing & zeroRing > oneRing) {
            //TensorFlow saw zero rings the most
            bot.clawClosePosition();
            bot.goForward(0.5, 5);
            bot.turnLeft(85, 0.3);
            bot.goForward(0.5, 25);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 68);
            //sleep(500);
            //vuNav.navigate(14, 60, 0);
            //sleep(500);
            //vuNav.navigate(14, 60, 0);
            //bot.clawOpenPosition();
        }

        nulvin.noVuforia();

        while (opModeIsActive()) {

        }
    }
}
