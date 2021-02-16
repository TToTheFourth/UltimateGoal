package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
class ExampleWithTeam extends LinearOpMode {
    RepresentoBotMVP bot = new RepresentoBotMVP(this);

    @Override
    public void runOpMode() {
        bot.startGyro();
        bot.goForward(0.3, 25);
        bot.turnRight(90, 0.3);
        bot.goForward(0.3, 40);
        bot.turnLeft(90,0.3);
        bot.goForward(0.3,6);
    }
}
