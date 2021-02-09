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
        int zeroRing=0;
        int oneRing=0;
        int fourRing=0;

        int sampleCount = 0;
        while(sampleCount < 10) {
            rings = nulvin.getRings();

            if(rings >= 0) {
                sampleCount++;

                if (rings == 0) {
                    zeroRing = zeroRing + 1;
                } else if (rings == 1) {
                    oneRing = oneRing + 1;
                } else if (rings == 4) {
                    fourRing = fourRing + 1;
                }
            }
        }

        telemetry.addData("Zero", zeroRing);
        telemetry.addData("One", oneRing);
        telemetry.addData("Four", fourRing);

        telemetry.update();

        while (opModeIsActive()){

        }

        nulvin.noVuforia();
    }
}
