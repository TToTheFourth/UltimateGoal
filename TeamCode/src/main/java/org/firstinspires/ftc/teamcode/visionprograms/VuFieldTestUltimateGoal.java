package org.firstinspires.ftc.teamcode.visionprograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraDevice;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.teamcode.lastYear.teamcode.RepresentoBotSupremeLeader;
import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.Directions;
import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.VuHolderSkyStone;


@Autonomous
public class VuFieldTestUltimateGoal extends LinearOpMode {
    @Override
    public void runOpMode() {

        VuHolder vu = new VuHolder(this);
        RepresentoBotSupremeLeader bot = new RepresentoBotSupremeLeader(this);
        float path[][] = {{0, 0, -3.14f/2}};

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        vu.activate();
        com.vuforia.Vuforia.init();
        float x;
        float y;
        float curx;
        float curry;
        float curh;
        bot.startGyro();
        bot.getReady(.9);
        if (!this.isStopRequested()) {
            for (int i = 1; i < path.length; i++) {
                if (vu.check() == true) {
                    vu.check();
                    x = path[i][0];
                    y = path[i][1];
                    Directions dirt = vu.getDirections(x, y);
                    telemetry.addData("heading %.1f", dirt.getHeading());
                    telemetry.addData("distance %.1f", dirt.getDistance());
                    telemetry.addData("Working", "YES");
                    float dirst = (float)Math.toDegrees(dirt.getHeading());
                    telemetry.addData("heading2 ", dirst);
                    telemetry.update();
                    float dist = dirt.getDistance();
                    if(dirst > 0){
                        bot.turnLeft(dirst, 0.3);
                    }else{
                        bot.turnRight(-dirst, 0.3);
                    }
                    bot.goForward(0.5, dist);
                } else {
                    telemetry.addData("Working", "NO");
                    telemetry.update();
                    curx = path[i - 1][0];
                    curry = path[i - 1][1];
                    curh = path[i - 1][2];
                    x = path[i][0];
                    y = path[i][1];
                    float dist = vu.getDistance(x, y, curx, curry);
                    float dir = vu.getDirection(x, y, curx, curry, curh, dist);
                    dir = (float)Math.toDegrees(dir);
                    if(dir > 0){
                        bot.turnLeft(dir, 0.3);
                    }else{
                        bot.turnRight(dir, 0.3);
                    }
                    bot.goForward(0.5, dist);
                }
            }
        }
        CameraDevice.getInstance().setFlashTorchMode(false);
        vu.deactivate();
        com.vuforia.Vuforia.deinit();
    }
}
