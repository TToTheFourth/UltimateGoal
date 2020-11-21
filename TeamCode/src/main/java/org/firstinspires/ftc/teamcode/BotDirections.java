package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

class BotDirections {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    private LinearOpMode opMode;

    public BotDirections(LinearOpMode om) {
        this.opMode = om;
        backLeft = opMode.hardwareMap.get(DcMotor.class, "motor0");
        frontLeft = om.hardwareMap.get(DcMotor.class, "motor1");
        frontRight = om.hardwareMap.get(DcMotor.class, "motor2");
        backRight = om.hardwareMap.get(DcMotor.class, "motor3");


        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

//    double frontLeft = (rightX_G1 + rightY_G1 - leftX_G1);
//    double backLeft = (rightX_G1 + rightY_G1 + leftX_G1);
//    double backRight = (rightX_G1 - rightY_G1 + leftX_G1);
//    double frontRight = (rightX_G1 - rightY_G1 - leftX_G1);
    //this is a function that forward
    public void moveForward(double inches, double power){
        frontLeft.setPower(power);
    }
}
