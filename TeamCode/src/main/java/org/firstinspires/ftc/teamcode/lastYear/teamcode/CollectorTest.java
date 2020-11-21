package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.Directions;
import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.VuHolderSkyStone;

@Disabled
@Autonomous
public class CollectorTest extends LinearOpMode {
    @Override
    public void runOpMode() {

        VuHolderSkyStone vu = new VuHolderSkyStone(this);
        RepresentoBotSupremeLeader bot = new RepresentoBotSupremeLeader(this);
        float path[][] = {{-35, -45, 3.14f/2}, {35, -45, 3.14f/2}, {35, 45, 3.14f/2}};

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        float x;
        float y;
        float curx;
        float curry;
        float curh;
        bot.startGyro();
        CameraDevice.getInstance().setFlashTorchMode(true);
        vu.activate();

            for (int i = 1; i < path.length && opModeIsActive(); i++) {
                while(opModeIsActive() && !vu.check()) {
                    bot.turnRight(10, 0.3);
                    sleep(1000);
                }
                if (vu.check() && opModeIsActive()) {
                    x = path[i][0];
                    y = path[i][1];
                    Directions dirt = vu.getDirections(x, y);
                    float dirst = (float) Math.toDegrees(dirt.getHeading());
                    float dist = dirt.getDistance();
                    telemetry.addData("","(%.2f, %.2f), %.2f", vu.xCoor(), vu.yCoor(), Math.toDegrees(vu.heading()));
                    telemetry.addData("","heading %.2f distance %.2f", dirst, dist);
                    telemetry.update();
                    sleep(10000);
                    if (dirst > 0) {
                        bot.turnLeft(dirst, 0.3);
                    } else {
                        bot.turnRight(-dirst, 0.3);
                    }
                    bot.goForward(0.5, dist);
                }
            }
        bot.stopMotor();
    }
}
