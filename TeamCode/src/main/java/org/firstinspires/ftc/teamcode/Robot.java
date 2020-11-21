package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class Robot extends LinearOpMode {
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

       waitForStart();

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while(opModeIsActive()) {

        }


    }
}
