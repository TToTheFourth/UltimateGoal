package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class VuforiaNavigator {
    LinearOpMode opMode;
    UltimateVuforia vu;
    RepresentoBotMVP bot;
    CoordHolder cH;


    // TODO: pass in UltimateVuforia instead of creating it here
    public VuforiaNavigator(LinearOpMode op, RepresentoBotMVP b) {
        bot = b;
        opMode = op;
        vu = new UltimateVuforia(op);
    }

    // TODO: remove
    public void yesVuforia() {
        // TODO: tell vu to turn on
        vu.yesVuforia();
    }

    // TODO: remove
    public void noVuforia() {
        // TODO: tell vu to turn off
        vu.noVuforia();
    }

    public void navigate(float targetX, float targetY, float targetAngle) {
        cH = vu.getCoords();
        if (cH.angle > targetAngle ) {
            bot.turnRight(cH.angle, .25);
            //assuming cH is based on angle right
        } else if (cH.angle < targetAngle ) {
            bot.turnLeft(-cH.angle, .25);
            //assuming cH is based on angle right
        }

        // If X < 43 then go farward the difference; if X > 43 then backup the difference
        if (cH.x < targetX) {
            bot.goForward(.5, (targetX - cH.x));
            //assuming slide left is positive
        } else if (cH.x > targetX) {
            bot.goForward(-.5, (cH.x - targetX));
            //assuming slide left is positive
        }
        // If Y < 35 then go slide left the difference; if Y > 35 then slide right the difference
        if (cH.y < targetY) {
            bot.slide(-.5, (targetY - cH.y));
        } else if (cH.y > 35) {
            bot.slide(.5, (cH.y - targetY));
        }
    }
}
