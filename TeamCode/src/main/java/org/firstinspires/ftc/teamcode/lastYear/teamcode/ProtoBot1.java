package org.firstinspires.ftc.teamcode.lastYear.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@Autonomous
public class ProtoBot1 extends LinearOpMode {

    private DcMotor rackMotor;

    @Override
    public void runOpMode() {
        rackMotor = hardwareMap.get(DcMotor.class, "spinner");


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        rackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while (opModeIsActive()) {

            rackMotor.setPower (1);
        }

        rackMotor.setPower(0);
    }

}

