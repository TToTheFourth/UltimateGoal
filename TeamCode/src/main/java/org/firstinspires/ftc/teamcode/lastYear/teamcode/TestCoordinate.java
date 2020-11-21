package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.Directions;
import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.VuHolderSkyStone;

@Disabled
@Autonomous
public class TestCoordinate extends LinearOpMode {
    @Override
public void runOpMode() {
        RepresentoBotSupremeLeader bot = new RepresentoBotSupremeLeader(this);
        VuHolderSkyStone vu = new VuHolderSkyStone(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while(!isStopRequested()){
            if(vu.check() == true) {
                telemetry.addData("X Coordinate", vu.xCoor());
                telemetry.addData("Y Coordinate", vu.yCoor());
                telemetry.addData("R Heading", vu.heading());
                Directions dir = vu.getDirections(-35, 0);
                telemetry.addData("distance ", dir.getDistance());
                telemetry.addData("heading ", Math.toDegrees(dir.getHeading()));
                telemetry.update();
            }else {
                telemetry.addData("Working", "NO");
                telemetry.update();
            }
        }
    }
}
