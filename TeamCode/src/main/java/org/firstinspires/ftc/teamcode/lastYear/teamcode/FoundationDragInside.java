package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Disabled
public class FoundationDragInside extends LinearOpMode {
    @Override
    public void runOpMode() {

        Timer timer = new Timer();
        RepresentoBotSupremeLeader bot = new RepresentoBotSupremeLeader(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        bot.startGyro();
        //bot.getReady(.1);
        bot.ServoUnlatch();
        // sets servos in starting position
        timer.waitT(8000);
        // waits, just in case an alliance partner places a block on the foundation
        bot.goForwardWithoutFix(-0.5, 1);
        // gets the robot off the wall
        bot.slideWithoutFix(0.5, 8);
        // slides to the middle of the foundation
        bot.goForwardWithoutFix(-0.5, 31);
        // goes forward to the foundation
        bot.servoLatch();
        // grabs the foundation
        timer.waitT(1000);
        // waits for the servos to fully latch onto the foundation
        bot.goForwardWithoutFix(0.5, 33);
        // goes backwards to the wall
        bot.slideWithoutFix(0.5, 5);
        // slides into the corner to maximize the success of the program
        bot.goForwardWithoutFix(0.5, 6.5);
        // pulls back to make sure the robot is as far against the wall as it needs to be
        timer.waitT(1000);
        // makes sure the robot has stopped moving
        bot.ServoUnlatch();
        // releases the foundation
        timer.waitT(1000);
        // waiting to make sure the foundation will not be dragged more by the servos
        bot.slideWithoutFix(-0.75, 35);
        bot.goForwardWithoutFix(-0.5, 30);
        bot.slideWithoutFix(-0.75, 18);
        // robot slides onto line
        //timer.waitT(3000);
        //bot.timeRackOut(4.5f);
        // moves the rack out, so it doesn't need to come out later
    }
}
