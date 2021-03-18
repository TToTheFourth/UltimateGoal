package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Vuforia;


@TeleOp
public class MechDrive2020 extends LinearOpMode {

    private DcMotor backLeftMotor;
    private DcMotor frontLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor frontRightMotor;
//    RepresentoBotMVP bot;
//    UltimateVuforia vu;
//    VuforiaNavigator vuNav;

    private DcMotor thrower;
    private DcMotor convoy;
    private Servo claw;
    private CRServo sweep2;
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


    float rpm = 0f;

    class RPMSampler extends Thread {
        @Override
        public void run() {
            while (opModeIsActive()) {
                rpm = getRPM();
            }
        }
    }


    @Override
    public void runOpMode() {

        //bot=new RepresentoBotMVP(this);
        //vu = new UltimateVuforia(this);
        //vuNav = new VuforiaNavigator(this, bot, vu);
        //bot.startGyro();
        //Vuforia.init();

        backLeftMotor = hardwareMap.get(DcMotor.class, "motor0");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "motor1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "motor2");
        backRightMotor = hardwareMap.get(DcMotor.class, "motor3");

        thrower = hardwareMap.get(DcMotor.class, "thrower");
        convoy = hardwareMap.get(DcMotor.class, "convey2");
        claw = hardwareMap.get(Servo.class, "claw");
        elbow = hardwareMap.get(DcMotor.class, "elbow");
        miniSweep = hardwareMap.get(Servo.class, "servoSweep");
        sweep2 = hardwareMap.get(CRServo.class, "sweep2");
        sweeper = hardwareMap.get(DcMotor.class, "sweeper");

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sweeper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // makes sure the robot doesn't drift

        // initialize the encoder
        thrower.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        thrower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        Thread t = new Thread(new RPMSampler());
        t.start();

        while (opModeIsActive()) {

            double rightX_G1;
            double rightY_G1;
            double leftX_G1;
            double leftY_G1;

            rightY_G1 = -gamepad1.right_stick_y;
            rightX_G1 = -gamepad1.right_stick_x;
            leftY_G1 = gamepad1.left_stick_y;
            leftX_G1 = -gamepad1.left_stick_x;

            if (gamepad1.x) {
                speed = 0.5;
            } else if (gamepad1.y) {
                speed = 1.0;
            }

            double frontLeft = (rightX_G1 + rightY_G1 - leftX_G1) * (speed);
            double backLeft = (rightX_G1 + rightY_G1 + leftX_G1) * (speed);
            double backRight = (rightX_G1 - rightY_G1 + leftX_G1) * (speed);
            double frontRight = (rightX_G1 - rightY_G1 - leftX_G1) * (speed);


            frontLeftMotor.setPower(frontLeft);
            backLeftMotor.setPower(backLeft);
            backRightMotor.setPower(backRight);
            frontRightMotor.setPower(frontRight);


            if (gamepad2.left_bumper) {
                thrower.setPower(1);
            } else {
                thrower.setPower(0);
            }

            if (gamepad2.dpad_down) {
                convoy.setPower(0.5);
            } else if (gamepad2.dpad_up){
                convoy.setPower(-0.5);
            } else if (gamepad2.a) {
                // button to run conveyor only when it is > 415 RPM
                if (rpm > 415) {
                    convoy.setPower(0.5);
                } else {
                    convoy.setPower(0);
                }
            }   else {
                convoy.setPower(0);
            }

            if (gamepad2.right_trigger > 0) {
                elbow.setPower(0.5);
            } else if (gamepad2.left_trigger > 0) {
                elbow.setPower(-0.5);
            } else {
                elbow.setPower(0);
            }

            if (gamepad2.right_bumper) {
                claw.setPosition(-1.0);
            } else {
                claw.setPosition(1.0);
            }

            if (gamepad2.dpad_right) {
                miniSweep.setPosition(1);
                sweep2.setPower(1);
            } else {
                miniSweep.setPosition(0);
                sweep2.setPower(0);
            }

            if (gamepad2.b) {
                sweeper.setPower(1);
            } else if (gamepad2.y){
                sweeper.setPower(-1);
            } else {
                sweeper.setPower(0);
            }
        }

        // turns off motors
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
        convoy.setPower(0);
        thrower.setPower(0);
        elbow.setPower(0);

        // make sure the rpm sampler thread wakes up and stops if it is sleeping
        t.interrupt();
    }

    private float getRPM() {
        float degPerTick = 1.01f;
        long start;
        long startTicks = 0;
        long endTicks = 0;
        long stop;

        // capture the start time
        start = System.currentTimeMillis();

        // capture the start ticks on the motor
        startTicks = thrower.getCurrentPosition();


        sleep(1000);

        // capture the end ticks on the motor
        endTicks = thrower.getCurrentPosition();

        // capture the end time
        stop = System.currentTimeMillis();

        // calculate the average rpm for this second
        float r = (endTicks - startTicks) * degPerTick / (stop - start) * 166.67f;

        return r;
    }
}