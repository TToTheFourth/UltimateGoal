package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.UltimateVuforia;
import org.firstinspires.ftc.teamcode.VuforiaNavigator;

@Autonomous
public class TestCaseBoxFour extends LinearOpMode {
    RepresentoBotMVP bot;
    UltimateVuforia vu;
    VuforiaNavigator vuNav;
//    int rings;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        vu = new UltimateVuforia(this);
        vuNav = new VuforiaNavigator(this, bot, vu);
        waitForStart();
        bot.startGyro();
        vu.yesVuforia();

//        rings = vu.tensorflow();
//        telemetry.addData("rings", rings);
//        telemetry.update();
//        if (rings == 4) {
        bot.clawClosePosition();
        bot.dropSweep();

        bot.goForwardGyroErrorCorrection(0.5, 53);
        bot.shootRings(3);

        bot.goForwardGyroErrorCorrection(0.5, 63);
        bot.slide(-0.5, 12);
        //change 12 -> 10
        bot.turnRight(83, 0.3);
        bot.goForwardGyroErrorCorrection(-0.5, 8);
        bot.clawOpenPosition();
        bot.goForwardGyroErrorCorrection(0.5, 10);
        bot.turnRight(90, 0.3);
        bot.goForwardGyroErrorCorrection(0.5, 50);
        // where are we on the XY grid?
        CoordHolder c = vu.getCoords();
        if(c.seeImage) {

            // Correct to point 0 degrees (forward)
            if(c.angle > 0) {
                bot.turnRight(c.angle, 0.3);
            } else if(c.angle < 0) {
                bot.turnLeft(-c.angle, 0.3);
            }

            // How far to backup to -48?
            bot.goForwardGyroErrorCorrection(-0.3, 48 - c.x);

            // How far to slide left to get to 48?
            bot.slide(-0.3, 48 - c.y);

            // grab goal
            bot.clawClosePosition();

            // go to the box
            bot.goForwardGyroErrorCorrection(0.5, 118);
            bot.slide(-0.5, 12);

            // drop the goal
            bot.clawOpenPosition();

            // go back to the line
            bot.slide(0.5, 18);
            bot.goForwardGyroErrorCorrection(-0.5, 58);
        }
//        }
        vu.noVuforia();

    }
}
