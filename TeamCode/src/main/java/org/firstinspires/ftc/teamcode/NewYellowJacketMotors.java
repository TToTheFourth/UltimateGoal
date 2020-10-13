package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.lastYear.teamcode.RepresentoBotSupremeLeader;

@TeleOp
public class NewYellowJacketMotors extends LinearOpMode {

    private DcMotor backLeftMotor;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    @Override
    public void runOpMode() {
        RepresentoBotMVP  bot = new RepresentoBotMVP(this);

        backLeftMotor = hardwareMap.get(DcMotor.class, "motor0");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor2");
        backRightMotor = hardwareMap.get(DcMotor.class, "motor3");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        double tgtPowerLB = 0;
        double tgtPowerRB = 0;
        double tgtPowerLF = 0;
        double tgtPowerRF = 0;

        tgtPowerLB = -gamepad1.left_stick_y;
        tgtPowerRF = gamepad1.right_stick_y;
        tgtPowerLF = -gamepad1.left_stick_y;
        tgtPowerRB = gamepad1.right_stick_y;

        while (opModeIsActive()) {

            frontLeftMotor.setPower(tgtPowerLF);
            backLeftMotor.setPower(tgtPowerLB);
            frontRightMotor.setPower(tgtPowerRF);
            backRightMotor.setPower(tgtPowerRB);

            telemetry.addData("Target Power Left Back", tgtPowerLB);
            telemetry.addData("Target Power Right Back", tgtPowerRB);
            telemetry.addData("Target Power Left Front", tgtPowerLF);
            telemetry.addData("Target Power Right Front", tgtPowerRF);

        }

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);

    }
}
