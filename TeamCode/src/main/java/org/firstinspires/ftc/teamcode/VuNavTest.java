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
        waitForStart();
        bot.startGyro();

        Vuforia.init();

        waitForStart();

        // Navigate from (-72, 35) to (43, 35)
        // go forward 115 inches
        bot.goForward(1,115);
        //TODO: ask Vuforia if we are at (43, 35) heading is 0 degrees; if an image is seen then:
        if(cH.seeImage) {
            vu.getCoords();
            // TODO:     if heading is not zero, then turn robot to correct
            if (cH.angle != 0) {
                bot.turnLeft(cH.angle, .25);
                //assuming cH is based on angle right
            }
            // TODO:     if X < 43 then go farward the difference; if X > 43 then backup the difference
            if (cH.x < 43) {
                bot.goForward(1, (43 - cH.x));
                //assuming slide left is positive
            } else if (cH.x > 43) {
                bot.goForward(-1, (cH.x - 43));
                //assuming slide left is positive
            }
            // TODO:     if Y < 35 then go slide left the difference; if Y > 35 then slide right the difference
            if (cH.y < 35) {
                bot.slide(-1, (35 - cH.y));
            } else if (cH.y > 35) {
                bot.slide(1, (cH.y - 35));
            }
        }
        // TODO: if image not seen do nothing
        // TODO: turn off Vuforia
        Vuforia.deinit();
    }
}
