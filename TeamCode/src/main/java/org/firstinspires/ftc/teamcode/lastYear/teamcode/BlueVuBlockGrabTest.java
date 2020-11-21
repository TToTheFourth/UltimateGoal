package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.Directions;
import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.VuHolderSkyStone;

@Autonomous
@Disabled
public class BlueVuBlockGrabTest extends LinearOpMode {
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
            bot.forwardUntilDistance(10, 0.4);
//            if (!vu.checkForSkystone()) {
//                vu.checkForSkystone();
            for (int g = 0; g < 6; g++) {
                if (!opModeIsActive()) {
                    break;
                }
                if (vu.checkForSkystone()) {
                    bot.slide(0.5, 8); //this should go left
                    break;
                }
                bot.slide(-0.7, 6);
                sleep(500);
            }
//            bot.slide(-0.4, 8);
            bot.goForward(0.5, 6);
            bot.getReady(.3);
            bot.goForward(-0.8, 20);
            boolean sawTarget = false;
            for (int i = 0; i < 6; i++) {
                if (!opModeIsActive()) {
                    break;
                }
                bot.turnLeft(11, 0.5);
                sleep(250);
                if (vu.check()) {
                    sawTarget = true;
                    break;
                }
                //todo: change to less degrees?
            }
            if (sawTarget && opModeIsActive()) {
                Directions dirt = vu.getDirections(45, 45);
                float dirst = (float) Math.toDegrees(dirt.getHeading());
                float dist = dirt.getDistance();
                if (dirst > 0) {
                    bot.turnLeft(dirst, 0.3);
                } else {
                    bot.turnRight(-dirst, 0.3);
                }
                bot.goForward(0.5, dist);
                bot.getReady(.9);
            } else {
                bot.forwardUntilColor(0.5);
                bot.goForward(0.5, 22);
                bot.getReady(0.9);
                bot.forwardUntilColor(-0.5);
//                bot.stopMotor();
            }

//             {
//                telemetry.addData("Working", "NO");
//                telemetry.update();
        }
//                bot.timeRackOut(4.5f);
        bot.stopMotor();
        // }
        bot.stopMotor();
        vu.deactivate();
    }
}
