package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;
import org.firstinspires.ftc.teamcode.UltimateVuforia;
import org.firstinspires.ftc.teamcode.VuforiaNavigator;

@Autonomous
public class MainAutoWithoutVu extends LinearOpMode {
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

        rings = vu.tensorflow();
        telemetry.addData("rings", rings);
        telemetry.update();
        if (rings == 4) {
            bot.turnLeft(85, 0.3);
            bot.goForward(0.5, 24);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 115);
        } else if (rings == 1) {
            bot.goForward(0.5, 96);
        } else {
            bot.clawClosePosition();
            bot.goForward(0.5, 5);
            bot.turnLeft(85, 0.3);
            bot.goForward(0.5, 25);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 65);
            bot.clawOpenPosition();
        }
        vu.noVuforia();
    }
}
