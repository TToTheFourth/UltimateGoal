package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Vuforia;

@Autonomous
public class VuNavTestWithClass extends LinearOpMode {
    RepresentoBotMVP bot;
    CoordHolder cH;
    VuforiaNavigator vuNav;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        vuNav = new VuforiaNavigator(this, bot);
        waitForStart();
        bot.startGyro();
        vuNav.yesVuforia();

        // Navigate from (-72, 35) to (43, 35)
        // go forward 115 inches
        bot.goForward(.5,100);
        sleep(3000);

        // Ask Vuforia if we are at (43, 35) heading is 0 degrees; if an image is seen then:
        vuNav.navigate(43, 35, 0);
        // If image not seen do nothing

        // Turn off Vuforia
        vuNav.noVuforia();
    }
}
