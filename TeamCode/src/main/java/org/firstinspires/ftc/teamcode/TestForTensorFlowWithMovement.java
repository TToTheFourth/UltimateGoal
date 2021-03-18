package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.UltimateVuforia;

@Autonomous
public class TestForTensorFlowWithMovement extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        UltimateVuforia vu = new UltimateVuforia(this);
        RepresentoBotMVP bot = new RepresentoBotMVP(this);
        VuforiaNavigator vuNav = new VuforiaNavigator(this, bot, vu);

        vu.yesVuforia();
        bot.startGyro();

        waitForStart();

        int rings;
        float zeroRing=0;
        float oneRing=0;
        float fourRing=0;
        float conf = 1;

        int sampleCount = 0;
        while(sampleCount < 10) {
            RingResult result = vu.getRings();
            rings = result.getRingCount();
            conf = result.getConfidence();

            if(rings >= 0) {
                sampleCount++;

                if (rings == 0) {
                    zeroRing = (zeroRing + (1 * conf));
                } else if (rings == 1) {
                    oneRing = (oneRing + (1 * conf));
                } else if (rings == 4) {
                    fourRing = (fourRing + (1 * conf));
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
            bot.dropSweep();
            bot.goForwardGyroErrorCorrection(0.5, 53);
            bot.shootRings(3);
            bot.goForwardGyroErrorCorrection(0.5, 63);
            bot.slide(-0.5, 12);
            bot.turnRight(83, 0.3);
            bot.goForwardGyroErrorCorrection(-0.5, 8);
            bot.clawOpenPosition();
            bot.goForwardGyroErrorCorrection(0.5, 10);
            bot.turnRight(90, 0.3);
            bot.goForwardGyroErrorCorrection(0.5, 50);
        } else if (oneRing > fourRing & oneRing > zeroRing) {
            //TensorFlow saw one ring the most
            bot.clawClosePosition();
            bot.dropSweep();
            bot.goForwardGyroErrorCorrection(0.5, 53);
            bot.shootRings(3);
            bot.goForwardGyroErrorCorrection(0.5, 59);
            bot.clawOpenPosition();
            bot.slide(0.5, 12);
            bot.goForwardGyroErrorCorrection(-0.5, 37);
        } else if (zeroRing > fourRing & zeroRing > oneRing) {
            //TensorFlow saw zero rings the most
            bot.clawClosePosition();
            bot.dropSweep();
            bot.goForwardGyroErrorCorrection(0.5, 53);
            bot.shootRings(3);
            bot.slide(-0.5, 12);
            bot.goForwardGyroErrorCorrection(0.5, 28);
            bot.clawOpenPosition();
            bot.slide(0.5, 18);
            bot.goForwardGyroErrorCorrection(-0.5, 12);
        }

        vu.noVuforia();

        while (opModeIsActive()) {

        }
    }
}
