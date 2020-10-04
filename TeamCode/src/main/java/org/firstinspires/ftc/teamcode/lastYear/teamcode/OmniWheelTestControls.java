package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp

public class OmniWheelTestControls extends LinearOpMode {
    private DcMotor backLeftMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor frontRightMotor;

    @Override
    public void runOpMode() {

        backLeftMotor = hardwareMap.get(DcMotor.class, "motor0");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor2");
        backRightMotor = hardwareMap.get(DcMotor.class, "motor3");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        double tgtPowerLB = 0;
        double tgtPowerRB = 0;
        double tgtPowerLF = 0;
        double tgtPowerRF = 0;
        double factor = 2;

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while (opModeIsActive()) {

            double ch1;
            double ch2;
            double ch3;
            double ch4;

            ch1 = gamepad1.right_stick_x;
            ch2 = gamepad1.right_stick_y;
            ch3 = -gamepad1.left_stick_y;
            ch4 = -gamepad1.left_stick_x;

            if(gamepad1.y){
                factor = 1;
            }else if(gamepad1.a) {
                factor = 3;
            }else if(gamepad1.x){
                factor = 2;
            }

            frontLeftMotor.setPower((ch3 - ch4 + ch1)/factor);
            backLeftMotor.setPower((ch3 + ch4 + ch1)/factor);
            backRightMotor.setPower((ch3 - ch4 - ch1)/factor);
            frontRightMotor.setPower((ch3 + ch4 - ch1)/factor);

        }

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}
