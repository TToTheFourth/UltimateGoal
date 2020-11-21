package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.Directions;
import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.VuHolderSkyStone;

@Disabled
@Autonomous
public class BlockTransportVuTest extends LinearOpMode {
    @Override
    public void runOpMode() {

        Timer timer = new Timer();
        RepresentoBotSupremeLeader bot = new RepresentoBotSupremeLeader(this);
        VuHolderSkyStone vu = new VuHolderSkyStone(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        bot.getReady(.9);
       // vu.activate();
        bot.startGyro();
       // vu.check();
        if(vu.check() == true) {
            Directions dir = vu.getDirections(-36,70 );
            telemetry.addData("YES", "Working");
            telemetry.update();
        } else {
            telemetry.addData("NO", "Working");
            telemetry.update();
        }
       // vu.deactivate();
    }
}