package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.UltimateVuforia;
import org.firstinspires.ftc.teamcode.VuforiaNavigator;

@Autonomous
public class TestCaseBoxOne extends LinearOpMode {
    RepresentoBotMVP bot;
    UltimateVuforia vu;
    VuforiaNavigator vuNav;
//    int rings;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        vu = new UltimateVuforia(this);
        vuNav = new VuforiaNavigator(this, bot, vu);
        bot.startGyro();
        vu.yesVuforia();

        waitForStart();
        bot.clawClosePosition();
        bot.dropSweep();

        bot.goForwardGyroErrorCorrection(0.5, 53);
        bot.shootRings(3);

        bot.goForwardGyroErrorCorrection(0.5, 59);
        bot.slide(1, 3);
        bot.clawOpenPosition();
        bot.slide(1, 12);

        // TODO: default coordinates by dead reckoning
        float xpos = 48f; // left back wheel
        float ypos = 29f; // left back wheel

        CoordHolder c = vu.getCoords();
        // TODO: if we see an image update the coordinates.
        if (c.seeImage == true){
            xpos = c.x;
            ypos = c.y;
        }

        bot.goForwardGyroErrorCorrection(-0.5, 85.5);
        bot.slide(-1, 21);



        /*bot.goForwardGyroErrorCorrection(-0.5, 37);
        // we should be at (36, 36)

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

            // go from (-48, 48) to the box (36, 36)

bot.goForwardGyroErrorCorrection(0.5, 84);
bot.goForwardGyroErrorCorrection(-0.5, 70);

            // drop the goal
            bot.clawOpenPosition();

            // go back to the line
            bot.slide(0.5, 18);
            bot.goForwardGyroErrorCorrection(-0.5, 12);
        }
*/
        vu.noVuforia();
    }
}