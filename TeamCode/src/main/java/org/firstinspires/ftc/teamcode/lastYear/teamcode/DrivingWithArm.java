package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp
public class DrivingWithArm extends LinearOpMode {

    private DcMotor backLeftMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor frontRightMotor;
    private DcMotor trackMotor;
    private Servo stringServo;

    @Override
    public void runOpMode() {

        backLeftMotor = hardwareMap.get(DcMotor.class, "motor0");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor2");
        backRightMotor = hardwareMap.get(DcMotor.class, "motor3");
        trackMotor = hardwareMap.get(DcMotor.class, "motor4");
        stringServo = hardwareMap.get(Servo.class, "servo1");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        double tgtPowerLB = 0;
        double tgtPowerRB = 0;
        double tgtPowerLF = 0;
        double tgtPowerRF = 0;
        double tgtPowerTM = 0;
        double tgtPowerSS = 0;
        double factor = 2;
        double multiplier = 1;
        boolean wheelsOn;

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        trackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // makes sure the robot doesn't drift

        while (opModeIsActive()) {

            double rightX_G1;
            double rightY_G1;
            double leftX_G1;
            double leftY_G1;

            rightY_G1 = -gamepad1.right_stick_y;
            rightX_G1 = -gamepad1.right_stick_x;
            leftY_G1 = gamepad1.left_stick_y;
            leftX_G1 = -gamepad1.left_stick_x;
            tgtPowerSS = gamepad2.left_stick_y;
            tgtPowerTM = gamepad2.right_stick_y;
            // sets the power to a controller


            if (gamepad1.y) {
                factor = 1;
            } else if (gamepad1.a) {
                factor = 3;
            } else if (gamepad1.x) {
                factor = 2;
            }
            // allows the speed tyo change

            boolean power = false;
            boolean steady = false;
            double nPower = 0;

            nPower = (tgtPowerLB + tgtPowerLF + tgtPowerRB + tgtPowerRF) / 4;

            if (nPower > 0) {
                power = true;
            }

            if (power == true && steady == false && multiplier < 1) {
                multiplier = multiplier + 0.1;
            } else if (power = false) {
                multiplier = 0;
                steady = false;
            } else if (multiplier >= 1) {
                steady = true;
            }
            // makes the robot gradually increase speed

            frontLeftMotor.setPower(((rightX_G1 + rightY_G1 - leftX_G1) / factor) * multiplier);
            backLeftMotor.setPower(((rightX_G1 + rightY_G1 + leftX_G1) / factor) * multiplier);
            backRightMotor.setPower(((rightX_G1 - rightY_G1 + leftX_G1) / factor) * multiplier);
            frontRightMotor.setPower(((rightX_G1 - rightY_G1 - leftX_G1) / factor) * multiplier);
            trackMotor.setPower(tgtPowerTM);
            stringServo.setPosition(tgtPowerSS);
            // makes the correct motor / servo connect to the correct power variable(s)

        }

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        // ends motor movement
    }
}
