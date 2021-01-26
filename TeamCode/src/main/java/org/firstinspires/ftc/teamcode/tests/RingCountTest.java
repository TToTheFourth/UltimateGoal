package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.UltimateVuforia;
import org.firstinspires.ftc.teamcode.VuforiaNavigatorTelemOutput;

@TeleOp
public class RingCountTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        UltimateVuforia Vu = new UltimateVuforia(this);
        Vu.yesVuforia();
        waitForStart();

        sleep(2000);
        int rings = Vu.tensorflow();
        telemetry.addData("Final Rings", rings);
        telemetry.update();
        while (opModeIsActive()){

        }

        Vu.noVuforia();
    }
}
