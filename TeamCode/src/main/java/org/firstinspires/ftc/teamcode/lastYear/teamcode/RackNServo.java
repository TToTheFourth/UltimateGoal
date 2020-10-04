package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp
public class RackNServo extends LinearOpMode {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor rackMotor;
    private Servo squeezeServo;

    @Override
    public void runOpMode() {
        rackMotor = hardwareMap.get(DcMotor.class, "motor0");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor1");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor2");
        squeezeServo = hardwareMap.get(Servo.class, "servo0");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        double tgtPowerFLM = 0;
        double tgtPowerFRM = 0;
        double tgtPowerRM = 0;
        double factor = 2;
        double tgtPowerSqueezeServo = 0;

        rackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while (opModeIsActive()) {

            tgtPowerFLM = gamepad2.left_stick_y;
            tgtPowerFRM = gamepad2.right_stick_y;
            tgtPowerRM = -gamepad1.left_stick_y;
            tgtPowerSqueezeServo = this.gamepad1.right_stick_y;

            if(gamepad1.y){
                factor = 1;
            }else if(gamepad1.a) {
                factor = 3;
            }else if(gamepad1.x){
                factor = 2;
            }

            frontLeftMotor.setPower(tgtPowerFLM/factor);
            frontRightMotor.setPower(tgtPowerFRM/factor);
            rackMotor.setPower(tgtPowerRM);
            squeezeServo.setPosition(tgtPowerSqueezeServo);

            telemetry.addData("Target Power Rack Motor", tgtPowerRM);
            telemetry.addData("Target Power Left Front", tgtPowerFLM);
            telemetry.addData("Target Power Right Front", tgtPowerFRM);
            telemetry.addData("Target Power Squeeze Servo", tgtPowerSqueezeServo);

        }

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rackMotor.setPower(0);
        squeezeServo.setPosition(0);
    }

}
