package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.UltimateVuforia;
import org.firstinspires.ftc.teamcode.VuforiaNavigator;
import org.tensorflow.lite.TensorFlowLite;

@Autonomous
public class TestCaseBoxZero extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RepresentoBotMVP bot=new RepresentoBotMVP(this);
        UltimateVuforia vu = new UltimateVuforia(this);

        bot.startGyro();
        vu.yesVuforia();

        waitForStart();

        bot.clawClosePosition();
        bot.dropSweep();

        bot.goForwardGyroErrorCorrection(0.5, 53);
        bot.shootRings(3);

        bot.slide(-0.5, 12);
        bot.goForwardGyroErrorCorrection(0.5, 35);
        bot.clawOpenPosition();
        bot.slide(0.5, 12);
        //bot.goForwardGyroErrorCorrection(-0.5, 18);
        //bot.turnLeft(90, 0.3);

        // where are we on the XY grid?
//        CoordHolder c = vu.getCoords();
//        if(c.seeImage == false) {
//            bot.goForwardGyroErrorCorrection(0.4, 4);
//            sleep(200);
//            c = vu.getCoords();
//
//            if(c.seeImage == false) {
//                bot.turnLeft(5, 0.3);
//                sleep(200);
//                c = vu.getCoords();
//
//                if(c.seeImage == false) {
//                    bot.turnRight(10, 0.3);
//                    sleep(200);
//                    c = vu.getCoords();
//                }
//            }
//        }

        // TODO: default coordinates by dead reckoning
        float xpos = 27.5f; // left back wheel
        float ypos = 42.5f; // left back wheel

        CoordHolder c = vu.getCoords();
        // TODO: if we see an image update the coordinates.
        if (c.seeImage == true){
            xpos = c.x;
            ypos = c.y;
        }
        // TODO: navigate from current coords to -48, 48
        bot.goForwardGyroErrorCorrection(-0.5, xpos + 38);
        bot.slide(-0.5, 51.5-ypos);

        bot.clawClosePosition();
        bot.goForwardGyroErrorCorrection(0.5, 34);
        bot.turnRight(120, 0.5);
        sleep(300);
        /*
        if(c.seeImage) {

            // Correct to point 0 degrees (forward)
            if(c.angle > 0) {
                bot.turnRight(c.angle, 0.3);
            } else if(c.angle < 0) {
                bot.turnLeft(-c.angle, 0.3);
            }

            // How far to backup to -48?
            bot.goForwardGyroErrorCorrection(-0.5, 43 - c.x);

            // How far to slide left to get to 48?
            bot.slide(-0.5, 51.5 - c.y);

            sleep(3000);

            // grab goal
            bot.clawClosePosition();

            sleep(3000);

            // go to the box
            bot.goForwardGyroErrorCorrection(0.5, 45);
            bot.slide(-0.5, 12);

            // drop the goal
            bot.clawOpenPosition();

            // go back to the line
            bot.slide(0.5, 18);
            bot.goForwardGyroErrorCorrection(-0.5, 12);
        } else {
            telemetry.addData("Image", "No");
            telemetry.update();
        }
        */

        vu.noVuforia();

        while (opModeIsActive()) {
            idle();
        }
    }

}
