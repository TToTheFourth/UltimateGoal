package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class CountTicks extends LinearOpMode {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    @Override
    public void runOpMode() {
        backLeft = hardwareMap.get(DcMotor.class, "motor0");
        frontLeft = hardwareMap.get(DcMotor.class, "motor1");
        frontRight = hardwareMap.get(DcMotor.class, "motor2");
        backRight = hardwareMap.get(DcMotor.class, "motor3");


        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        // initialize the encoder
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // remove -->RepresentoBotMVP  bot = new RepresentoBotMVP(this);

        motorslide();
        long ticks;

        while (opModeIsActive()) {

            ticks = backLeft.getCurrentPosition();
            if(ticks < 0) {
                ticks = -ticks;
            }

            if (ticks >= 3000) {
                break;
            }

            // if ticks >= 5000 break
            telemetry.addData("ticks ", ticks);
            telemetry.update();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        // turn off motors
    }

    public void motorsforward (){

        double rightY_G1 = -0.5;
        double rightX_G1 = -0;
        double leftY_G1 = -0;
        double leftX_G1 = -0;

        double frontLeftp = (rightX_G1 + rightY_G1 - leftX_G1);
        double backLeftp = (rightX_G1 + rightY_G1 + leftX_G1);
        double backRightp = (rightX_G1 - rightY_G1 + leftX_G1);
        double frontRightp = (rightX_G1 - rightY_G1 - leftX_G1);

        // turn on the motors to forward for 3000 ticks
        frontLeft.setPower(frontLeftp);
        frontRight.setPower(frontRightp);
        backLeft.setPower(backLeftp);
        backRight.setPower(backRightp);

    }
    public void motorslide (){

    double rightY_G1 = -0;
    double rightX_G1 = -0;
    double leftY_G1 = -0;
    double leftX_G1 = -0.5;

    double frontLeftp = (rightX_G1 + rightY_G1 - leftX_G1);
    double backLeftp = (rightX_G1 + rightY_G1 + leftX_G1);
    double backRightp = (rightX_G1 - rightY_G1 + leftX_G1);
    double frontRightp = (rightX_G1 - rightY_G1 - leftX_G1);

    // turn on the motors to forward for 3000 ticks
        frontLeft.setPower(frontLeftp);
        frontRight.setPower(frontRightp);
        backLeft.setPower(backLeftp);
        backRight.setPower(backRightp);

}

}
