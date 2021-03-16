package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class VuforiaNavigator {
    LinearOpMode opMode;
    UltimateVuforia vu;
    RepresentoBotMVP bot;
    CoordHolder cH;

    public VuforiaNavigator(LinearOpMode op, RepresentoBotMVP b, UltimateVuforia v) {
        bot = b;
        opMode = op;
        vu = v;
    }

    public void navigate(float targetX, float targetY, float targetAngle) {

        // loop up to 10 times
        //   if you see an image break
        //   else wait 500ms
        // end

        for (int i = 0; i < 10; i++){
            cH = vu.getCoords();
            if (cH.seeImage == true){
                break;

            } else {
                opMode.sleep(500);
            }
        }

        if(cH.seeImage) {
            opMode.telemetry.addData("X", String.format("%.1f", cH.x));
            opMode.telemetry.addData("Y", String.format("%.1f", cH.y));

            // Get the robot on the desired heading (targetAngle heading)
            if (cH.angle > targetAngle) {
                bot.turnRight(cH.angle - targetAngle, .25);
                //assuming cH is based on angle right
            } else if (cH.angle < targetAngle) {
                bot.turnLeft(targetAngle - cH.angle, .25);
                //assuming cH is based on angle right
            }

            // Move to the desired X coordinate
            if (cH.x < targetX) {
                bot.goForward(.5, (targetX - cH.x));
                //assuming slide left is positive
            } else if (cH.x > targetX) {
                bot.goForward(-.5, (cH.x - targetX));
                //assuming slide left is positive
            }

            // Move to the desired Y coordinate
            if (cH.y < targetY) {
                bot.slide(-.5, (targetY - cH.y));
            } else if (cH.y > targetY) {
                bot.slide(.5, (cH.y - targetY));
            }
        } else {
            opMode.telemetry.addData("No Image", ":(");
            opMode.telemetry.update();
        }
    }
}
