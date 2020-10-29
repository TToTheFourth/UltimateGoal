package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutoBot extends LinearOpMode {
    RepresentoBotMVP bot;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);

        //see how many rings there are
        int rct = 0;

        if (rct == 0) {
            bot.forwardUntilColor(0.5);
            bot.slide(0.5, 23);
            //drop goal
            bot.slide(0.5,-46);
            bot.goForward(0.5,-18);
            //shoot rings
            bot.goForward(0.5,18);
        } else if (rct==1) {
            bot.forwardUntilColor(0.5);
            bot.goForward(0.5, 23);
            //drop goal
            bot.goForward(0.5, -41);
            //shoot rings
            bot.goForward(0.5, 18);
        }

        else{
                bot.forwardUntilColor(0.5);
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
}
