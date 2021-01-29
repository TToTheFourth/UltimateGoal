package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Vuforia;


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
    private DcMotor thrower;
    private DcMotor convoy;
    private Servo claw;
    private DcMotor elbow;
    private Servo miniSweep;
    private DcMotor sweeper;
    public double speed = 1;

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
        Vuforia.init();

        backLeftMotor = hardwareMap.get(DcMotor.class, "motor0");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor2");
        backRightMotor = hardwareMap.get(DcMotor.class, "motor3");

        // TODO: get claw and elbow motors

        thrower = hardwareMap.get(DcMotor.class, "thrower");
        convoy = hardwareMap.get(DcMotor.class, "convey2");
        claw = hardwareMap.get(Servo.class, "claw");
        elbow = hardwareMap.get(DcMotor.class, "elbow");
        miniSweep = hardwareMap.get(Servo.class, "servoSweep");
        sweeper = hardwareMap.get(DcMotor.class, "sweeper");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sweeper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

            double frontLeft = (rightX_G1 + rightY_G1 - leftX_G1) * (speed);
            double backLeft = (rightX_G1 + rightY_G1 + leftX_G1) * (speed);
            double backRight = (rightX_G1 - rightY_G1 + leftX_G1) * (speed);
            double frontRight = (rightX_G1 - rightY_G1 - leftX_G1) * (speed);

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

//            if(gamepad1.a) {
//                vu.getCoords();
//                vuNav.navigate(-6,36, 0);
//                if(gamepad1.b) {
//                    break;
//                }
//            }
//
//            if (gamepad1.x){
//                vu.getCoords();
//                telemetry.addData("coords", vu.getCoords());
//            }

            if (gamepad2.left_bumper) {
                thrower.setPower(1);
            }

            if (gamepad1.left_bumper) {
                speed = 0.5;
            } else if (gamepad1.x) {
                speed = 0.25;
            } else if (gamepad1.y) {
                speed = 1;
            }
            //Todo: add choord for power shot

            // TODO: get input from controller dpad left - right for claw
            //if (gamepad2.dpad_left){
            //    claw.setPower(0.5);
            //} else if (gamepad2.x){
            ///    claw.setPower(-0.5);
            //} else {
            //    claw.setPower(0);
            //}

//            if(gamepad2.right_bumper) {
//                // thrower on
//                thrower.setPower(1);
//            } else {
//                // thrower off
//                thrower.setPower(0);
//            }

            // TODO: get input from controller dpad up - down for elbow
            if (gamepad2.dpad_down) {
                convoy.setPower(0.5);
                thrower.setPower(0);
            } else if (gamepad2.dpad_up){
                thrower.setPower(1);
                convoy.setPower(-0.5);
            } else {
                convoy.setPower(0);
                thrower.setPower(0);
            }

            if (gamepad2.right_bumper) {
                claw.setPosition(0.5);
            } else {
                claw.setPosition(0.3);
            }

            if (gamepad2.right_trigger > 0) {
                elbow.setPower(0.5);
            } else if (gamepad2.left_trigger > 0) {
                elbow.setPower(-0.5);
            } else {
                elbow.setPower(0);
            }

            if (gamepad2.dpad_right) {
                miniSweep.setPosition(1);
            } else {
                miniSweep.setPosition(0);
            }

            if (gamepad2.b) {
                sweeper.setPower(1);
            } else if (gamepad2.y){
                sweeper.setPower(-1);
            } else {
                sweeper.setPower(0);
            }

            telemetry.update();
        }

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        convoy.setPower(0);
        thrower.setPower(0);
        elbow.setPower(0);
        Vuforia.deinit();
        // turns off motors
    }
}