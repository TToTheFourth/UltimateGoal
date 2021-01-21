package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Vuforia;

import org.tensorflow.lite.TensorFlowLite;

@Autonomous
public class TestCaseBoxZero extends LinearOpMode {
    RepresentoBotMVP bot;
    CoordHolder cH;
    VuforiaNavigator vuNav;
    UltimateVuforia vu;
    int rings;

    @Override
    public void runOpMode() throws InterruptedException {
        bot=new RepresentoBotMVP(this);
        vu = new UltimateVuforia(this);
        waitForStart();
        bot.startGyro();
        vu.yesVuforia();

        rings = vu.tensorflow();
        telemetry.addData("rings", rings);
        telemetry.update();
        if (rings == 0) {
            bot.goForward(0.5, 5);
            bot.turnLeft(90, 0.3);
            bot.goForward(0.5, 19);
            bot.turnRight(90, 0.3);
            bot.goForward(0.5, 55);
        }
        vu.noVuforia();
    }
}
