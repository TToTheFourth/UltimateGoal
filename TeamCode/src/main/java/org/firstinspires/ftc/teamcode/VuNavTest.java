package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Vuforia;

@Autonomous
public class VuNavTest extends LinearOpMode {
    RepresentoBotMVP bot;
    UltimateVuforia vu;
    CoordHolder cH;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        vu = new UltimateVuforia(this);
        waitForStart();
        bot.startGyro();

        vu.yesVuforia();

        waitForStart();

        // Navigate from (-72, 35) to (43, 35)
        // go forward 115 inches
        bot.goForward(.75,100);
        // Ask Vuforia if we are at (43, 35) heading is 0 degrees; if an image is seen then:
        cH = vu.getCoords();
        if(cH.seeImage) {

            // If heading is not zero, then turn robot to correct
            if (cH.angle > 0 ) {
                bot.turnRight(cH.angle, .25);
                //assuming cH is based on angle right
            } else if (cH.angle < 0 ) {
                bot.turnLeft(-cH.angle, .25);
                //assuming cH is based on angle right
            }

            // If X < 43 then go farward the difference; if X > 43 then backup the difference
            if (cH.x < 43) {
                bot.goForward(.5, (43 - cH.x));
                //assuming slide left is positive
            } else if (cH.x > 43) {
                bot.goForward(-.5, (cH.x - 43));
                //assuming slide left is positive
            }
            // If Y < 35 then go slide left the difference; if Y > 35 then slide right the difference
            if (cH.y < 35) {
                bot.slide(-.5, (35 - cH.y));
            } else if (cH.y > 35) {
                bot.slide(.5, (cH.y - 35));
            }
        }
        // If image not seen do nothing

        // Turn off Vuforia
        vu.noVuforia();
    }
}
