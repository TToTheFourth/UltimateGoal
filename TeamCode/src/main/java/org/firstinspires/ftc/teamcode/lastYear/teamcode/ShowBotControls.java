package org.firstinspires.ftc.teamcode.lastYear.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp
public class ShowBotControls extends LinearOpMode {

    private DcMotor backLeftMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor frontRightMotor;
    private Servo iconServo;

    @Override
    public void runOpMode() {
        backLeftMotor = hardwareMap.get(DcMotor.class, "motor0");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor2");
        backRightMotor = hardwareMap.get(DcMotor.class, "motor3");
        iconServo = hardwareMap.get(Servo.class, "servo0");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        double tgtPowerLB = 0;
        double tgtPowerRB = 0;
        double tgtPowerLF = 0;
        double tgtPowerRF = 0;
        double tgtPowerIconServo = 0;
        double factor = 2;

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while (opModeIsActive()) {

            iconServo.setPosition(90);

            tgtPowerLB = -gamepad1.left_stick_y;
            tgtPowerRF = gamepad1.right_stick_y;
            tgtPowerLF = -gamepad1.left_stick_y;
            tgtPowerRB = gamepad1.right_stick_y;
            tgtPowerIconServo = this.gamepad1.left_trigger;

            if(gamepad1.y){
                factor = 1;
            }else if(gamepad1.a) {
                factor = 3;
            }else if(gamepad1.x){
                factor = 2;
            }

            frontLeftMotor.setPower(tgtPowerLF/factor);
            backLeftMotor.setPower(tgtPowerLB/factor);
            frontRightMotor.setPower(tgtPowerRF/factor);
            backRightMotor.setPower(tgtPowerRB/factor);
            iconServo.setPosition(tgtPowerIconServo);

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
