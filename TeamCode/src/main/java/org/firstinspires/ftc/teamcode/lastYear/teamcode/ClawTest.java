package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp
public class ClawTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        Servo claw = hardwareMap.get(Servo.class, "claw");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        claw.setPosition(0.5);
        while (opModeIsActive()) {


            //0.075
            double clawPos = gamepad2.right_trigger;
            if (clawPos < 0.17) {
                clawPos = 0.17;
            }
            if (clawPos > 0.65) {
                clawPos = 0.65;
            }
            claw.setPosition(clawPos);
            // releases the claw


        }


    }
}
