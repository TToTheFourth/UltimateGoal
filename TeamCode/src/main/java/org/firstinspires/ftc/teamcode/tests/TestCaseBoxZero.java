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
        bot.goForwardGyroErrorCorrection(-0.5, 18);
        bot.turnLeft(90, 0.3);
        //bot.goForwardGyroErrorCorrection(-0.5, 12);

        // where are we on the XY grid?
        CoordHolder c = vu.getCoords();
        if(c.seeImage == false) {
            bot.goForwardGyroErrorCorrection(0.4, 4);
            sleep(200);
            c = vu.getCoords();

            if(c.seeImage == false) {
                bot.turnLeft(5, 0.3);
                sleep(200);
                c = vu.getCoords();

                if(c.seeImage == false) {
                    bot.turnRight(10, 0.3);
                    sleep(200);
                    c = vu.getCoords();
                }
            }
        }
        /*
        CoordHolder c = null;
        for (int i = 0 ; i < 3; i++){
            c = vu.getCoords();
            if (c.seeImage == true){
                break;
            }else{
                bot.goForwardGyroErrorCorrection(0.3, 2);
            }
        }
        */

        if(c.seeImage) {

            // Correct to point 0 degrees (forward)
            if(c.angle > 0) {
                bot.turnRight(c.angle, 0.3);
            } else if(c.angle < 0) {
                bot.turnLeft(-c.angle, 0.3);
            }

            // How far to backup to -48?
            bot.goForwardGyroErrorCorrection(-0.5, 48 - c.x);

            // How far to slide left to get to 48?
            bot.slide(-0.5, 48 - c.y);

            // grab goal
            bot.clawClosePosition();

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

        vu.noVuforia();

        while (opModeIsActive()) {
            idle();
        }
    }
}
