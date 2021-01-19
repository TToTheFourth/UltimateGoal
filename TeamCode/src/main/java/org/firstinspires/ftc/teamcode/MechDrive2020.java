package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class MechDrive2020 extends LinearOpMode {

    private DcMotor backLeftMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor frontRightMotor;
    RepresentoBotMVP bot;
    UltimateVuforia vu;
    VuforiaNavigator vuNav;

    // TODO: declare motor for claw and elbow
    private DcMotor claw;
    private  DcMotor elbow;
    private DcMotor convoy;

    static final double INCREMENT   = 0.01;
    static final int    CYCLE_MS    =   50;
    static final double MAX_FWD     =  1.0;
    static final double MAX_REV     = -1.0;

    double  power   = 0;
    boolean rampUp  = true;

    @Override
    public void runOpMode() {

        bot=new RepresentoBotMVP(this);
        vu = new UltimateVuforia(this);
        vuNav = new VuforiaNavigator(this, bot, vu);
        waitForStart();
        bot.startGyro();
        vu.yesVuforia();

        backLeftMotor = hardwareMap.get(DcMotor.class, "motor0");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor2");
        backRightMotor = hardwareMap.get(DcMotor.class, "motor3");

        // TODO: get claw and elbow motors
        claw = hardwareMap.get(DcMotor.class, "claw0");
        elbow = hardwareMap.get(DcMotor.class, "elbow1");
        convoy = hardwareMap.get(DcMotor.class, "convey2");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        convoy.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

            double frontLeft = (rightX_G1 + rightY_G1 - leftX_G1);
            double backLeft = (rightX_G1 + rightY_G1 + leftX_G1);
            double backRight = (rightX_G1 - rightY_G1 + leftX_G1);
            double frontRight = (rightX_G1 - rightY_G1 - leftX_G1);

            telemetry.addData("frontLeft %.02f", frontLeft);
            telemetry.addData("frontRight %.02f", frontRight);
            telemetry.addData("backLeft %.02f", backLeft);
            telemetry.addData("backRight %.02f", backRight);
/*
            if (rampUp) {
                // Keep stepping up until we hit the max value.
                frontLeft += INCREMENT;
                frontRight += INCREMENT;
                backLeft += INCREMENT;
                backRight += INCREMENT;
                if (frontLeft >= MAX_FWD ) {
                    frontLeft = MAX_FWD;
                    rampUp = !rampUp;   // Switch ramp direction
                }
                if (frontRight >= MAX_FWD ) {
                    frontRight = MAX_FWD;
                    rampUp = !rampUp;   // Switch ramp direction
                }
                if (backLeft >= MAX_FWD ) {
                    backLeft = MAX_FWD;
                    rampUp = !rampUp;   // Switch ramp direction
                }
                if (backRight >= MAX_FWD ) {
                    backRight = MAX_FWD;
                    rampUp = !rampUp;   // Switch ramp direction
                }
            }
            else {
                // Keep stepping down until we hit the min value.
                frontLeft -= INCREMENT ;
                frontRight -= INCREMENT ;
                backLeft -= INCREMENT ;
                backRight -= INCREMENT ;
                if (frontLeft <= MAX_REV ) {
                    frontLeft = MAX_REV;
                    rampUp = !rampUp;  // Switch ramp direction
                }
                if (frontRight <= MAX_REV ) {
                    frontRight = MAX_REV;
                    rampUp = !rampUp;  // Switch ramp direction
                }
                if (backLeft <= MAX_REV ) {
                    backLeft = MAX_REV;
                    rampUp = !rampUp;  // Switch ramp direction
                }
                if (backRight <= MAX_REV ) {
                    backRight = MAX_REV;
                    rampUp = !rampUp;  // Switch ramp direction
                }
            }
*/
            frontLeftMotor.setPower(frontLeft);
            backLeftMotor.setPower(backLeft);
            backRightMotor.setPower(backRight);
            frontRightMotor.setPower(frontRight);

            if(gamepad1.a) {
                vu.getCoords();
                vuNav.navigate(50,50, 0 /*TODO: put in choords of where to shoot from, these are test choords*/);
            }

            //Todo: add choord for power shot location

















            // TODO: get input from controller dpad left - right for claw
            if (gamepad2.dpad_left){
                claw.setPower(0.5);
            } else if (gamepad2.dpad_right){
                claw.setPower(-0.5);
            } else {
                claw.setPower(0);
            }

            // TODO: get input from controller dpad up - down for elbow
            if (gamepad2.dpad_down){
                elbow.setPower(0.5);
            } else if (gamepad2.dpad_up){
                elbow.setPower(-0.5);
            } else {
                elbow.setPower(0);
            }

            if (gamepad2.a){
                convoy.setPower(1);
            } else if (gamepad2.y){
                convoy.setPower(-1);
            } else {
                convoy.setPower(0);
            }

            telemetry.update();
        }

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        convoy.setPower(0);
        elbow.setPower(0);
        claw.setPower(0);
        // turns off motors
    }
}