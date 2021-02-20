package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.UltimateVuforia;

@Autonomous
public class TenserFlowTest3 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        UltimateVuforia nulvin = new UltimateVuforia(this);
        nulvin.yesVuforia();

        waitForStart();

        int rings;
        float zeroRing=0;
        float oneRing=0;
        float fourRing=0;
        float conf = 1;

        int sampleCount = 0;
        while(sampleCount < 10) {
            rings = nulvin.getRings();
            conf = nulvin.getConfidence();

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
