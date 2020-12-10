package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class VuNavTest extends LinearOpMode {
    RepresentoBotMVP bot;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        waitForStart();
        bot.startGyro();

        // TODO: initialize Vuforia

        waitForStart();

        // Navigate from (-72, 35) to (43, 35)
        // TODO: tell robot to go forward 115 inches
        // TODO: ask Vuforia if we are at (43, 35) heading is 0 degrees; if an image is seen then:
        // TODO:     if heading is not zero, then turn robot to correct
        // TODO:     if X < 43 then go farward the difference; if X > 43 then backup the difference
        // TODO:     if Y < 35 then go slide left the difference; if Y > 35 then slide right the difference
        // TODO: if image not seen do nothing

        // TODO: turn off Vuforia
    }
}
