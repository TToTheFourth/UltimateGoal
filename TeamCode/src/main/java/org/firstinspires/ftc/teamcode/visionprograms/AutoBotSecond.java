/* Copyright (c) 2019 FIRST. All rights reserved.
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

package org.firstinspires.ftc.teamcode.visionprograms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.RepresentoBotMVP;

import java.util.List;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Ultimate Goal game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@TeleOp
public class AutoBotSecond extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    RepresentoBotMVP  bot = new RepresentoBotMVP(this);

    private static final String VUFORIA_KEY = "AcUwNff/////AAABmSDjtLt8eEYos7+P16Q5uMpfai9UDhOe3GPRF9oLweSCr+ydsB1z1O07EAL8u6QzfDIp2DKNqqxD7AzaTUEjHxLeL/W86upMAQ/yj+i0xCTmb46d6WyaCEK//pGA1eXtYAUzXizSQiLvp3ljz1d27Lv8xsJb+RQqFRW+IgJ/k+McoNBZF6v9Y+huXNSZhUtfqklrr4IhP64h9DGxrAst7swmUES4fsMGXRAF+p2sJlgv9cKtJpdKo6e0xSbN5Oe4+0nGKhvwIO7qGW4tQLM/1h1VlJoQWhT8N42Ccho4cu83IcFN1WIqSpz7KsGEuqEonrf2S4tthqMJ+FE5f4cYY0jgfkxaPf4y0GjKR0cggrXS";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();
        }

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("height", i), recognition.getHeight());
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            if(recognition.getLabel() == LABEL_SECOND_ELEMENT) {
                                bot.forwardUntilPink(0.5);
                                bot.goForward(0.5, 23);
                                //drop goal
                                bot.goForward(0.5, -41);
                                //shoot rings
                                bot.goForward(0.5, 18);

                                /*grab wobble
                                add color sensor to find line
                                from line you go to one of 3 variables
                                go back to line
                                go to specific point behind line
                                shoot 3 rings
                                find line with color sensor
                                stop on line
                                single:
                                shoot rings at goal
                                take wobble to corresponding box
                                stop on line
                                */
                            } else if(recognition.getLabel() == LABEL_FIRST_ELEMENT) {
                                bot.forwardUntilPink(0.5);
                                bot.goForward(0.5,46);
                                bot.slide(0.5, 23);
                                //drop goal
                                bot.slide(0.5, -46);
                                bot.goForward(0.5, -64);
                                //shoot rings
                                bot.goForward(0.5, 18);

                                /*grab wobble
                                add color sensor to find line
                                from line you go to one of 3 variables
                                go back to line
                                go to specific point behind line
                                shoot 3 rings
                                find line with color sensor
                                stop on line
                                quad:
                                shoot rings at goal
                                take wobble to corresponding box
                                stop on line
                                */
                            } else {
                                bot.forwardUntilPink(0.5);
                                bot.slide(0.5, 23);
                                //drop goal
                                bot.slide(0.5,-46);
                                bot.goForward(0.5,-18);
                                //shoot rings
                                bot.goForward(0.5,18);

                                /*grab wobble
                                add color sensor to find line
                                from line you go to one of 3 variables
                                go back to line
                                go to specific point behind line
                                shoot 3 rings
                                find line with color sensor
                                stop on line
                                no rings:
                                shoot rings at goal
                                take wobble to corresponding box
                                stop on line
                                */
                            }
                        }
                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
