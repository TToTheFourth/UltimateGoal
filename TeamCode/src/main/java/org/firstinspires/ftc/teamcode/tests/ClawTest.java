package org.firstinspires.ftc.teamcode.tests;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ClawTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo claw = hardwareMap.get(Servo.class, "claw");

        waitForStart();

        //claw.setPosition(-1.0);
        int lastDir = 0;
        while (opModeIsActive()) {

            if (gamepad1.dpad_left) {
                lastDir = -1;
                claw.setPosition(-1.0);
                telemetry.addLine("left");
                telemetry.addData("position %.3f", claw.getPosition());
                telemetry.update();
            } else if (gamepad1.dpad_right) {
                lastDir = 1;
                claw.setPosition(1.0);
                telemetry.addLine("right");
                telemetry.addData("position %.3f", claw.getPosition());
                telemetry.update();
            } else {
                lastDir = 0;
                claw.setPosition(0.5);
                telemetry.addLine("stop");
                telemetry.addData("position %.3f", claw.getPosition());
                telemetry.update();
            }


        }

        //claw.setPower(0);
    }
}
