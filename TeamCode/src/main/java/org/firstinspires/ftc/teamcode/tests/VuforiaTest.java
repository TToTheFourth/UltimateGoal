package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CoordHolder;
import org.firstinspires.ftc.teamcode.UltimateVuforia;

@Autonomous
public class VuforiaTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
//        vu --> new ultimate vuforia
//        vu.yesVuforia() --turn it on
//
//        while op mode is active
//        vu.getCoords()
//        output to the phone:
//        did it see an image
//        what are x, y, angle values
//                end
        UltimateVuforia vu = new UltimateVuforia(this);
        vu.yesVuforia();
        waitForStart();
        while(opModeIsActive()){
           CoordHolder ch =  vu.getCoords();
           if(ch.seeImage==true) {
               telemetry.addData("x", ch.x);
               telemetry.addData("y", ch.y);
               telemetry.addData("angle", ch.angle);
               telemetry.update();
           }
           else{
               telemetry.addData("seeimage",false);
               telemetry.update();
           }
        }

    }
}
