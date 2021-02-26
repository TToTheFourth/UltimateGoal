package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AutoBot extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        UltimateVuforia nulvin = new UltimateVuforia(this);
        nulvin.yesVuforia();

        RepresentoBotMVP bot = new RepresentoBotMVP(this);
        bot.startGyro();
        sleep(4000);

        telemetry.addData("Status", "Ready");
        telemetry.update();

        waitForStart();
        bot.clawClosePosition();
        bot.dropSweep();
        bot.goForwardGyroErrorCorrection(0.3, 21);

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
        telemetry.addData("Confidence", conf);

        telemetry.update();

        nulvin.noVuforia();


        bot.goForwardGyroErrorCorrection(0.3, 32);
        bot.shootRings(3);
        if (zeroRing > oneRing && zeroRing > fourRing){
            bot.slide(-0.5, 12);
            bot.goForwardGyroErrorCorrection(0.5, 35); // 28
            bot.clawOpenPosition();
            bot.slide(0.5, 18);
            bot.goForwardGyroErrorCorrection(-0.5, 19); // 12
        }
        else if (fourRing > oneRing && fourRing > zeroRing){
            bot.goForwardGyroErrorCorrection(0.5, 70); // 70
            bot.slide(-0.5, 8);
            //change 12 -> 10
            bot.turnRight(83, 0.3);
            bot.goForwardGyroErrorCorrection(-0.5, 8);
            bot.clawOpenPosition();
            bot.goForwardGyroErrorCorrection(0.5, 10);
            bot.turnRight(90, 0.3);
            bot.goForwardGyroErrorCorrection(0.5, 57); // 50
        }
        else {
            bot.goForwardGyroErrorCorrection(0.5, 66); // 59
            bot.clawOpenPosition();
            bot.slide(0.5, 12);
            bot.goForwardGyroErrorCorrection(-0.5, 44); // 37
        }
    }
}
