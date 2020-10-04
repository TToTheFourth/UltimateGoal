package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class BlueBlockTransport extends LinearOpMode {
    @Override
    public void runOpMode() {

        Timer timer = new Timer();
        RepresentoBotSupremeLeader bot = new RepresentoBotSupremeLeader(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        bot.startGyro();
        bot.getReady(.9);
        bot.forwardUntilDistance(2, 0.3);
        //the robot goes forward until the range sensor is close to the blocks,
        // so we don't have to rely on how many inches we need to go
        bot.moveClaw(1);
        // puts claw down on the first block it interacts with ( usually first or second)
        timer.waitT(2000);
        //waits for claw to fully grab block
        bot.goForward(-0.5, 26);
        // back away from blocks
        bot.turnLeft(80, 0.5 );
        // turns to face build site
        bot.goForward(0.5,50);
        // goes into build site
        bot.moveClaw(0);
        // releases block
        bot.goForward(0.5, -8);
        // todo: is needed?
        // moves to stop on line
        bot.goForward(-0.5, 20);
        // moves to stop on line
        bot.timeRackOut(4.5f);
        // moves the rack out, so it doesn't need to come out later
    }
}
