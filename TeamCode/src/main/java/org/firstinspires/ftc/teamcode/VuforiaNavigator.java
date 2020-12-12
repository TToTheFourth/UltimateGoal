package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class VuforiaNavigator {
    LinearOpMode opMode;
    UltimateVuforia vu;
    RepresentoBotMVP bot;

    public VuforiaNavigator(LinearOpMode op, RepresentoBotMVP b) {
        bot = b;
        opMode = op;
        // TODO: set the vu variable to a new UltimateVuforia
    }

    public void yesVuforia() {
        // TODO: tell vu to turn on
    }

    public void noVuforia() {
        // TODO: tell vu to turn off
    }

    public void navigate(float targetX, float targetY, float targetAngle) {
        // TODO: use logic from VuNavTest here...
    }
}
