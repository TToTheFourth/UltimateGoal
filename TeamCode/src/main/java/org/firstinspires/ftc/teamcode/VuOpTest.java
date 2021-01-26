package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class VuOpTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        UltimateVuforia Vu = new UltimateVuforia(this);
        VuforiaNavigatorTelemOutput nav = new VuforiaNavigatorTelemOutput(this, Vu);
        Vu.yesVuforia();
        waitForStart();

        while (opModeIsActive()){
        CoordHolder coord2= Vu.getCoords();
        if (coord2.seeImage) {
            //telemetry.addData("x", coord2.x);
            //telemetry.addData("y", coord2.y);
            //telemetry.addData("angle", coord2.angle);
            nav.navigate(36, 36, 0);
        } else {
            telemetry.addData("noImage", "");
        }
        telemetry.update();
        }

        Vu.noVuforia();
    }
}
