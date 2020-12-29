/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.colorSensor;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class ColorCalabb extends LinearOpMode {

    //ColorSensor sensorColor;
    NormalizedColorSensor sensorColor;
    DistanceSensor sensorDistance;

    @Override
    public void runOpMode() {

        // get a reference to the color sensor.
        sensorColor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        // get a reference to the distance sensor that shares the same name.
        //sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // wait for the start button to be pressed.
        waitForStart();

        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.

        List<Float> h = new ArrayList<>();
        List<Float> s = new ArrayList<>();
        List<Float> v = new ArrayList<>();
        // makes lists for h, s, and v to contain the numbers
        float hsum = 0;
        // the sum of all h values
        float ssum = 0;
        // the sum of all s values
        float vsum = 0;
        // the sum of all v values

        for(int i = 0; i < 2000; i++) {

            NormalizedRGBA colors = sensorColor.getNormalizedColors();
            // normalizes the colors so none of the colors have readings that are off the mark
            // ( a jump of 50 to 1059 )
            float max = Math.max(Math.max(Math.max(colors.red, colors.green), colors.blue), colors.alpha);
            colors.red   /= max;
            colors.green /= max;
            colors.blue  /= max;

            Color.colorToHSV(colors.toColor(), hsvValues);
            // changes rbg to hsv

            telemetry.addData("H  ", hsvValues[0]);
            telemetry.addData("S", hsvValues[1]);
            telemetry.addData("V ", hsvValues[2]);
            telemetry.update();
            // let's u see the hsv values on the screen

            h.add(hsvValues[0]);
            s.add(hsvValues[1]);
            v.add(hsvValues[2]);
            // applying the hsv values to their lists
        }

        for (int i = 0; i < h.size(); i++) {
            hsum = hsum + h.get(i);
            // adds all the h values together
        }
        for (int i = 0; i < s.size(); i++) {
            ssum = ssum + s.get(i);
            // adds all the s values together
        }
        for (int i = 0; i < v.size(); i++) {
            vsum = vsum + v.get(i);
            // adds all the v values together
        }

        float have = hsum / h.size();
        float save = ssum / s.size();
        float vave = vsum / v.size();
        // averages the hsv values

        hsum = 0;
        ssum = 0;
        vsum = 0;
        // resets the sums

        for (int i = 0; i < h.size(); i++) {
            hsum = hsum + (float) Math.pow(h.get(i) - have, 2);
            // getting the standard deviation of h
        }
        for (int i = 0; i < s.size(); i++) {
            ssum = ssum + (float) Math.pow(s.get(i) - save, 2);
            // getting the standard deviation of s
        }
        for (int i = 0; i < v.size(); i++) {
            vsum = vsum + (float) Math.pow(v.get(i) - vave, 2);
            // getting the standard deviation of v
        }

        float hsd = (float) Math.sqrt(hsum / h.size());
        float ssd = (float) Math.sqrt(ssum / s.size());
        float vsd = (float) Math.sqrt(vsum / v.size());
        // averaging the hsv values


        telemetry.addData("H Average", have);
        telemetry.addData("S Average", save);
        telemetry.addData("V Average", vave);
        telemetry.addData("H Standard Deviation", hsd);
        telemetry.addData("S Standard Deviation", ssd);
        telemetry.addData("V Standard Deviation", vsd);
        telemetry.addData("H Standard Deviation Range Upper", have + 3 * hsd);
        telemetry.addData("H Standard Deviation Range Lower", have - 3 * hsd);
        telemetry.addData("S Standard Deviation Range Upper", save + 3 * ssd);
        telemetry.addData("S Standard Deviation Range Lower", save - 3 * ssd);
        telemetry.addData("V Standard Deviation Range Upper", vave + 3 * vsd);
        telemetry.addData("V Standard Deviation Range Lower", vave - 3 * vsd);
        telemetry.update();
        // for testing we added the different hsv values to telemetry so we knew what numbers to use


        while (opModeIsActive()) {
                // convert the RGB values to HSV values.
                // multiply by the SCALE_FACTOR.
                // then cast it back to int (SCALE_FACTOR is a double)
                //Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                 //       (int) (sensorColor.green() * SCALE_FACTOR),
                  //      (int) (sensorColor.blue() * SCALE_FACTOR),
                  //      hsvValues);

                // send the info back to driver station using telemetry function.
                //telemetry.addData("Distance (cm)",
                //String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
                //telemetry.addData("Alpha", sensorColor.alpha());
                //telemetry.addData("Red  ", sensorColor.red());
                //telemetry.addData("Green", sensorColor.green());
                //telemetry.addData("Blue ", sensorColor.blue());
                //telemetry.addData("Hue", hsvValues[0]);

                // change the background color to match the color detected by the RGB sensor.
                // pass a reference to the hue, saturation, and value array as an argument
                // to the HSVToColor method.
                //relativeLayout.post(new Runnable() {
                 //   public void run() {
                 //       relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                 //   }
                //});
                //telemetry.update();
            }

            // Set the panel back to the default color
            //relativeLayout.post(new Runnable() {
            //    public void run() {
            //        relativeLayout.setBackgroundColor(Color.WHITE);
            //    }
            //});
    }
}
