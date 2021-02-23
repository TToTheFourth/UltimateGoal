package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;

@TeleOp
public class GoalDropTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor elbow = hardwareMap.get(DcMotor.class, "elbow");
        Servo claw = hardwareMap.get(Servo.class, "claw");
        waitForStart();

claw.setPosition(-1);
        elbow.setPower(-0.5);
        sleep(800);
        elbow.setPower(0);
        sleep(2000);
    }
}
