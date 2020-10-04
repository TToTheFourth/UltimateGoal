package org.firstinspires.ftc.teamcode.lastYear.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

@Disabled
@TeleOp
public class StopAtColorLine extends LinearOpMode {

    //ColorSensor sensorColor;
    NormalizedColorSensor sensorColor;
    DistanceSensor sensorDistance;

    @Override
    public void runOpMode() {

        // get a reference to the color sensor.
        RepresentoBotSupremeLeader bot = new RepresentoBotSupremeLeader(this);
        sensorColor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        // get a reference to the distance sensor that shares the same name.
        //sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // wait for the start button to be pressed.
        waitForStart();

        bot.startGyro();
        bot.forwardUntilColor(0.5);
        bot.goForward(0.5, 15);
    }
}
