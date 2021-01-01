package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Vuforia;

import org.tensorflow.lite.TensorFlowLite;

@Autonomous
public class VuNavTestWithClass extends LinearOpMode {
    RepresentoBotMVP bot;
    CoordHolder cH;
    VuforiaNavigator vuNav;
    UltimateVuforia vu;
    int rings;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        vu = new UltimateVuforia(this);
        vuNav = new VuforiaNavigator(this, bot, vu);
        waitForStart();
        bot.startGyro();
        vu.yesVuforia();

        // Navigate from (-72, 35) to (43, 35)
        // go forward 115 inches

        while (opModeIsActive()) {
            rings = vu.tensorflow();
           // telemetry.addData("rings", rings);
            //telemetry.update();
        }

        /*
        if (rings == 0) {
            //add dead reckoning
            bot.turnLeft(90, 0.3);
            bot.goForward(0.5, 24);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 55);
            vuNav.navigate(0, 60, 0);
            sleep(500);
            vuNav.navigate(0, 60, 0);
        } else if (rings == 1) {
            //add dead reckoning
            bot.goForward(0.5, 96);
            vuNav.navigate(36, 36, 0);
            sleep(500);
            vuNav.navigate(36, 36, 0);
        } else {
            //add dead reckoning
            bot.turnLeft(90, 0.3);
            bot.goForward(0.5, 24);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 115);
            vuNav.navigate(60, 60, 0);
            sleep(500);
            vuNav.navigate(60, 60, 0);
        }
        bot.goForward(.5,100);
        sleep(3000);

        // Ask Vuforia if we are at (43, 35) heading is 0 degrees; if an image is seen then:
        vuNav.navigate(43, 35, 0);
        sleep(500);
        vuNav.navigate(43,35,0);
        // If image not seen do nothing
*/
        // Turn off Vuforia
        vu.noVuforia();
    }
}
