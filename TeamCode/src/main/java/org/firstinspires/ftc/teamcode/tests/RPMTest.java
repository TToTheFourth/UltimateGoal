package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class RPMTest extends LinearOpMode {

    private DcMotor thrower;

    @Override
    public void runOpMode() throws InterruptedException {
        thrower = hardwareMap.get(DcMotor.class, "thrower");

        // initialize the encoder
        thrower.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        thrower.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        thrower.setPower(1.0);

        float degPerTick = 1.01f;
        long start;
        long startTicks = 0;
        long endTicks = 0;
        long stop;

        waitForStart();

        while (opModeIsActive()){
            start = System.currentTimeMillis();
            startTicks = thrower.getCurrentPosition();

            // capture the start ticks on the motor

            sleep(2000);
            // capture the end ticks on the motor
            endTicks = thrower.getCurrentPosition();
            // capture the end time
            stop = System.currentTimeMillis();
            // calculate the average rpm for this second
            float rpm = (endTicks - startTicks) * degPerTick / (stop - start) * 166.67f;
            // output to phone
            telemetry.addData("rpm", rpm);
            telemetry.update();

        }
    }
}
