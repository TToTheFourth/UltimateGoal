package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.UltimateVuforia;
import org.firstinspires.ftc.teamcode.VuforiaNavigator;

@Autonomous
public class TensorFlowTest extends LinearOpMode {
    RepresentoBotMVP bot;
    VuforiaNavigator vuNav;
    UltimateVuforia vu;
    int rings;

    @Override
    public void runOpMode() throws InterruptedException {
        vu = new UltimateVuforia(this);
        waitForStart();
        vu.yesVuforia();
        while (opModeIsActive()) {
            rings = vu.tensorflow();
            telemetry.addData("rings", rings);
            telemetry.update();
        }
        vu.noVuforia();
    }
}
