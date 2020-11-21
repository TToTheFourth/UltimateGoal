package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.Directions;
import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.VuHolderSkyStone;

@Autonomous
@Disabled
public class VuBlockGrabTest extends LinearOpMode {
    @Override
    public void runOpMode() {

        VuHolderSkyStone vu = new VuHolderSkyStone(this);
        RepresentoBotSupremeLeader bot = new RepresentoBotSupremeLeader(this);
        float path[][] = {{-35, -45, 3.14f / 2}, {-15, -45, 3.14f / 2}, {0, -45, 3.14f / 2}, {15, -40, 3.14f / 2}, {35, -35, 3.14f / 2}, {35, 15, 3.14f / 2}, {35, 0, 3.14f / 2}, {35, 15, 3.14f / 2}, {35, 35, 3.14f / 2}};

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        vu.activate();
        float x;
        float y;
        float curx;
        float curry;
        float curh;
        bot.startGyro();
        bot.getReady(.9);
        if (!this.isStopRequested()) {
            //bot.getReady(0.9);
            bot.timeRackOut(4f);
            CameraDevice.getInstance().setFlashTorchMode(true);
            bot.forwardUntilDistance(12, 0.4);
//            if (!vu.checkForSkystone()) {
//                vu.checkForSkystone();
            for (int g = 0; g < 6; g++) {
                if (!opModeIsActive()) {
                    break;
                }
                if (vu.checkForSkystone()) {
                    bot.slide(0.5, 8);
                    break;
                }
                bot.slide(0.6, 5);
                sleep(250);
            }
            bot.goForward(0.5, 5);
            bot.getReady(.3);
            bot.goForward(-0.5, 7.5);
            boolean sawTarget = false;
            for (int i = 0; i < 6; i++) {
                if (!opModeIsActive()) {
                    break;
                }
                bot.turnRight(11, 0.5);
                if (!opModeIsActive()) {
                    break;
                }
                if (vu.check()) {
                    sawTarget = true;
                    break;
                }
                //todo: change to less degrees?
            }
            if (sawTarget) {
                Directions dirt = vu.getDirections(45, -45);
                float dirst = (float) Math.toDegrees(dirt.getHeading());
                float dist = dirt.getDistance();
                if (dirst > 0) {
                    bot.turnLeft(dirst, 0.3);
                }
            } else {
                bot.forwardUntilColor(0.5);
                bot.goForward(0.5, 15);
                bot.getReady(0.9);
                bot.forwardUntilColor(-0.5);
            }
            vu.deactivate();
            bot.stopMotor();
        }
    }
}
