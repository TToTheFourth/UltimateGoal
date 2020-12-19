package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AutoBot extends LinearOpMode {
    RepresentoBotMVP bot;
    VuforiaNavigator vuNav;
    UltimateVuforia vision;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        waitForStart();
        bot.startGyro();

        // TODO: see how many rings there are
        int rct = 0;

        if (rct == 0) {
            //bot.forwardUntilPink(0.5);
            bot.goForwardNoGyro(0.5, 70);
            sleep(2000);

            bot.slide(-0.5, 23);
            //drop goal
            sleep(2000);

            bot.slide(0.5,46);
            sleep(2000);
            bot.goForwardNoGyro(-0.5,18);
            //shoot rings
            sleep(2000);

            bot.goForwardNoGyro(0.5,18);
        } else if (rct==1) {
            bot.forwardUntilPink(0.5);
            bot.goForward(0.5, 23);
            //drop goal
            bot.goForward(0.5, -41);
            //shoot rings
            bot.goForward(0.5, 18);
        } else {
                bot.forwardUntilPink(0.5);
                bot.goForward(0.5,46);
                bot.slide(0.5, 23);
                //drop goal
                bot.slide(0.5, -46);
                bot.goForward(0.5, -64);
                //shoot rings
                bot.goForward(0.5, 18);
        }
    }
}
