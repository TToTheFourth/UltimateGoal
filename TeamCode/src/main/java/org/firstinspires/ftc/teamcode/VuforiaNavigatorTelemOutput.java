package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.UltimateVuforia;

public class VuforiaNavigatorTelemOutput {
    LinearOpMode opMode;
    UltimateVuforia vu;
    CoordHolder cH;

    public VuforiaNavigatorTelemOutput(LinearOpMode op, UltimateVuforia v) {
        opMode = op;
        vu = v;
    }

    public void navigate(float targetX, float targetY, float targetAngle) {
        cH = vu.getCoords();

        if (cH.angle > targetAngle ) {
            opMode.telemetry.addData("Right", String.format("%.1f", cH.angle));
            opMode.telemetry.addData("Left", String.format("%.1f", 0.0));
        } else if (cH.angle < targetAngle ) {
            opMode.telemetry.addData("Right", String.format("%.1f", 0.0));
            opMode.telemetry.addData("Left", String.format("%.1f", -cH.angle));
        } else {
            opMode.telemetry.addData("Right", String.format("%.1f", 0.0));
            opMode.telemetry.addData("Left", String.format("%.1f", 0.0));
        }

        if (cH.x < targetX) {
            opMode.telemetry.addData("Forward", String.format("%.1f", (targetX - cH.x)));
            opMode.telemetry.addData("Backward", String.format("%.1f", 0.0));
        } else if (cH.x > targetX) {
            opMode.telemetry.addData("Forward", String.format("%.1f", 0.0));
            opMode.telemetry.addData("Backward", String.format("%.1f", (cH.x - targetX)));
        } else {
            opMode.telemetry.addData("Forward", String.format("%.1f", 0.0));
            opMode.telemetry.addData("Backward", String.format("%.1f", 0.0));
        }

        if (cH.y < targetY) {
            opMode.telemetry.addData("SlideLeft", String.format("%.1f", (targetY - cH.y)));
            opMode.telemetry.addData("SlideRight", String.format("%.1f", 0.0));
        } else if (cH.y > 35) {
            opMode.telemetry.addData("SlideLeft", String.format("%.1f", 0.0));
            opMode.telemetry.addData("SlideRight", String.format("%.1f", (cH.y - targetY)));
        } else {
            opMode.telemetry.addData("SlideLeft", String.format("%.1f", 0.0));
            opMode.telemetry.addData("SlideRight", String.format("%.1f", 0.0));
        }
    }
}
