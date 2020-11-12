package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class MechDrive2020 extends LinearOpMode {

    private DcMotor backLeftMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor frontRightMotor;
    private DcMotor goalclaw;
    private  DcMotor goalarm;
    @Override
    public void runOpMode() {

        backLeftMotor = hardwareMap.get(DcMotor.class, "motor0");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor2");
        backRightMotor = hardwareMap.get(DcMotor.class, "motor3");

        goalclaw = hardwareMap.get(DcMotor.class, "goalclaw");
        goalarm = hardwareMap.get(DcMotor.class, "goalarm");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // makes sure the robot doesn't drift

        while (opModeIsActive()) {

            double rightX_G1;
            double rightY_G1;
            double leftX_G1;
            double leftY_G1;
            double leftX_G2;
            double leftY_G2;
            double rightX_G2;
            double rightY_G2;
            double rightTrigger;

            rightY_G1 = -gamepad1.right_stick_y;
            rightX_G1 = -gamepad1.right_stick_x;
            leftY_G1 = gamepad1.left_stick_y;
            leftX_G1 = -gamepad1.left_stick_x;
            rightTrigger = gamepad1.right_trigger;

            if (gamepad1.dpad_right){
                goalclaw.setPower(1);
            } else if (gamepad1.dpad_left){
                goalclaw.setPower(-1);
            }


            frontLeftMotor.setPower(rightX_G1 + rightY_G1 - leftX_G1);
            backLeftMotor.setPower(rightX_G1 + rightY_G1 + leftX_G1);
            backRightMotor.setPower(rightX_G1 - rightY_G1 + leftX_G1);
            frontRightMotor.setPower(rightX_G1 - rightY_G1 - leftX_G1);
            // sets each motor to correct variables

        }

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        // turns off motors


    }

}