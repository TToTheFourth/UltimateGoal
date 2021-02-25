package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.RingResult;
import org.firstinspires.ftc.teamcode.UltimateVuforia;

@Autonomous
public class TenserFlowTest3 extends LinearOpMode {
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

        while (opModeIsActive()){

        }

        nulvin.noVuforia();
    }
}
