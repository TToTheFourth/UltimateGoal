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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

/**
 * This 2020-2021 OpMode illustrates the basics of using the Vuforia localizer to determine
 * positioning and orientation of robot on the ULTIMATE GOAL FTC field.
 * The code is structured as a LinearOpMode
 *
 * When images are located, Vuforia is able to determine the position and orientation of the
 * image relative to the camera.  This sample code then combines that information with a
 * knowledge of where the target images are on the field, to determine the location of the camera.
 *
 * From the Audience perspective, the Red Alliance station is on the right and the
 * Blue Alliance Station is on the left.

 * There are a total of five image targets for the ULTIMATE GOAL game.
 * Three of the targets are placed in the center of the Red Alliance, Audience (Front),
 * and Blue Alliance perimeter walls.
 * Two additional targets are placed on the perimeter wall, one in front of each Tower Goal.
 * Refer to the Field Setup manual for more specific location details
 *
 * A final calculation then uses the location of the camera on the robot to determine the
 * robot's location and orientation on the field.
 *
 * @see VuforiaLocalizer
 * @see VuforiaTrackableDefaultListener
 * see  ultimategoal/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */



public class UltimateVuforia {

    // IMPORTANT:  For Phone Camera, set 1) the camera source and 2) the orientation, based on how your phone is mounted:
    // 1) Camera Source.  Valid choices are:  BACK (behind screen) or FRONT (selfie side)
    // 2) Phone Orientation. Choices are: PHONE_IS_PORTRAIT = true (portrait) or PHONE_IS_PORTRAIT = false (landscape)
    //
    // NOTE: If you are running on a CONTROL HUB, with only one USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    //
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AcUwNff/////AAABmSDjtLt8eEYos7+P16Q5uMpfai9UDhOe3GPRF9oLweSCr+ydsB1z1O07EAL8u6QzfDIp2DKNqqxD7AzaTUEjHxLeL/W86upMAQ/yj+i0xCTmb46d6WyaCEK//pGA1eXtYAUzXizSQiLvp3ljz1d27Lv8xsJb+RQqFRW+IgJ/k+McoNBZF6v9Y+huXNSZhUtfqklrr4IhP64h9DGxrAst7swmUES4fsMGXRAF+p2sJlgv9cKtJpdKo6e0xSbN5Oe4+0nGKhvwIO7qGW4tQLM/1h1VlJoQWhT8N42Ccho4cu83IcFN1WIqSpz7KsGEuqEonrf2S4tthqMJ+FE5f4cYY0jgfkxaPf4y0GjKR0cggrXS";


    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members

    List<VuforiaTrackable> allTrackables;

    private LinearOpMode op;
    VuforiaTrackables targetsUltimateGoal;
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private TFObjectDetector tfod = null;
    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;
    public UltimateVuforia (LinearOpMode opMode){
        op = opMode;
    }

    // Turn on Vuforia
    public void yesVuforia() {
         /*
          * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
          * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
          * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
          */
         int cameraMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
         VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

         // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

         parameters.vuforiaLicenseKey = VUFORIA_KEY;
         parameters.cameraDirection = CAMERA_CHOICE;

         // Make sure extended tracking is disabled for this example.
         parameters.useExtendedTracking = false;

         //  Instantiate the Vuforia engine
         vuforia = ClassFactory.getInstance().createVuforia(parameters);

         // Load the data sets for the trackable objects. These particular data
         // sets are stored in the 'assets' part of our application.
         targetsUltimateGoal = this.vuforia.loadTrackablesFromAsset("UltimateGoal");
         VuforiaTrackable blueTowerGoalTarget = targetsUltimateGoal.get(0);
         blueTowerGoalTarget.setName("Blue Tower Goal Target");
         VuforiaTrackable redTowerGoalTarget = targetsUltimateGoal.get(1);
         redTowerGoalTarget.setName("Red Tower Goal Target");
         VuforiaTrackable redAllianceTarget = targetsUltimateGoal.get(2);
         redAllianceTarget.setName("Red Alliance Target");
         VuforiaTrackable blueAllianceTarget = targetsUltimateGoal.get(3);
         blueAllianceTarget.setName("Blue Alliance Target");
         VuforiaTrackable frontWallTarget = targetsUltimateGoal.get(4);
         frontWallTarget.setName("Front Wall Target");

         // For convenience, gather together all the trackable objects in one easily-iterable collection */
         allTrackables = new ArrayList<VuforiaTrackable>();
         allTrackables.addAll(targetsUltimateGoal);

         /**
          * In order for localization to work, we need to tell the system where each target is on the field, and
          * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
          * Transformation matrices are a central, important concept in the math here involved in localization.
          * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
          * for detailed information. Commonly, you'll encounter transformation matrices as instances
          * of the {@link OpenGLMatrix} class.
          *
          * If you are standing in the Red Alliance Station looking towards the center of the field,
          *     - The X axis runs from your left to the right. (positive from the center to the right)
          *     - The Y axis runs from the Red Alliance Station towards the other side of the field
          *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
          *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
          *
          * Before being transformed, each target image is conceptually located at the origin of the field's
          *  coordinate system (the center of the field), facing up.
          */

         //Set the position of the perimeter targets with relation to origin (center of field)
         redAllianceTarget.setLocation(OpenGLMatrix
                 .translation(0, -halfField, mmTargetHeight)
                 .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

         blueAllianceTarget.setLocation(OpenGLMatrix
                 .translation(0, halfField, mmTargetHeight)
                 .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
         frontWallTarget.setLocation(OpenGLMatrix
                 .translation(-halfField, 0, mmTargetHeight)
                 .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

         // The tower goal targets are located a quarter field length from the ends of the back perimeter wall.
         blueTowerGoalTarget.setLocation(OpenGLMatrix
                 .translation(halfField, quadField, mmTargetHeight)
                 .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
         redTowerGoalTarget.setLocation(OpenGLMatrix
                 .translation(halfField, -quadField, mmTargetHeight)
                 .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

         //
         // Create a transformation matrix describing where the phone is on the robot.
         //
         // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
         // Lock it into Portrait for these numbers to work.
         //
         // Info:  The coordinate frame for the robot looks the same as the field.
         // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
         // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
         //
         // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
         // pointing to the LEFT side of the Robot.
         // The two examples below assume that the camera is facing forward out the front of the robot.

         // We need to rotate the camera around it's long axis to bring the correct camera forward.
         if (CAMERA_CHOICE == BACK) {
             phoneYRotate = -90;
         } else {
             phoneYRotate = 90;
         }

         // Rotate the phone vertical about the X axis if it's in portrait mode
         if (PHONE_IS_PORTRAIT) {
             phoneXRotate = 90;
         }

         // Next, translate the camera lens to where it is on the robot.
         // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
         final float CAMERA_FORWARD_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
         final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
         final float CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

         OpenGLMatrix robotFromCamera = OpenGLMatrix
                 .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                 .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

         /**  Let all the trackable listeners know where the phone is.  */
         for (VuforiaTrackable trackable : allTrackables) {
             ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
         }

        initTfod();
        tfod.activate();
        tfod.setZoom(1.0, 16.0 / 9.0);

    }

     public int tensorflow () {
        int OTF = 0;
        if (tfod != null) {
            op.telemetry.addData("TFOD", "NOT NULL");
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                op.telemetry.addData("RECLIST", "NOT NULL");
                //op.telemetry.addData("# Object Detected", updatedRecognitions.size());
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    //if (recognition.getBottom()>= 800) {
                        //op.telemetry.addData(String.format("height", i), recognition.getHeight());
                        //op.telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        //op.telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                        //        recognition.getLeft(), recognition.getTop());
                        //op.telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                        //        recognition.getRight(), recognition.getBottom());
                        if (recognition.getLabel().equals(LABEL_SECOND_ELEMENT)) {
                            OTF = 1;
                        } else if (recognition.getLabel().equals(LABEL_FIRST_ELEMENT)) {
                            OTF = 4;
                        } else {
                            OTF = 0;
                        }

                        op.telemetry.addData("Rings", OTF);
                        op.telemetry.update();

                    //}
                }
                if(i == 0) {
                    //op.telemetry.addData("Rings", OTF);
                    //op.telemetry.update();
                }
                //op.telemetry.update();
            } else {
                op.telemetry.addData("RECLIST", "NULL");
                //op.telemetry.addData("# Object Detected", 0);
            }
        } else {
            op.telemetry.addData("TFOD", "NULL");
        }
         return OTF;
     }


     // Return the X, Y, Heading coordinates if Vuforia sees an image
     // if Vuforia sees am image then CoordHolder.seeImage is true; otherwise it is false
     public CoordHolder getCoords() {
         CoordHolder coord= new CoordHolder();

             // check all the trackable targets to see which one (if any) is visible.
             coord.seeImage= false;
             targetVisible = false;
             for (VuforiaTrackable trackable : allTrackables) {
                 if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                     op.telemetry.addData("Visible Target", trackable.getName());
                     targetVisible = true;
                     coord.seeImage= true;
                     // getUpdatedRobotLocation() will return null if no new information is available since
                     // the last time that call was made, or if the trackable is not currently visible.
                     OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                     if (robotLocationTransform != null) {
                         lastLocation = robotLocationTransform;
                     }
                     break;
                 }
             }

             // Provide feedback as to where the robot is located (if we know).
             if (targetVisible) {
                 // express position (translation) of robot in inches.
                 VectorF translation = lastLocation.getTranslation();

                    coord.x = translation.get(0) / mmPerInch;
                    coord.y = translation.get(1) / mmPerInch;

                 // express the rotation of the robot in degrees.
                 Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);

                 coord.angle= rotation.thirdAngle;
             }

         return coord;
     }

     // Turn off Vuforia
     public void noVuforia() {
         tfod.shutdown();
         tfod.deactivate();

         targetsUltimateGoal.deactivate();
     }

    private void initTfod() {
        int tfodMonitorViewId = op.hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", op.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.useObjectTracker = false;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);

    }

    public int getRings() {
        int rings = -1;
        int conf = 1;


        if (tfod != null) {

            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                op.telemetry.addData("# Object Detected", updatedRecognitions.size());

                if (updatedRecognitions.size() == 0) {
                    // empty list.  no objects recognized.
                    op.telemetry.addData("TFOD", "No items detected.");
                    op.telemetry.addData("Target Zone", "A");
                    rings = 0;
                } else {
                    // list is not empty.
                    // step through the list of recognitions and display boundary info.

                    // TODO: if there is only one item in the list then use that item's label
                    if (updatedRecognitions.size() == 1) {
                        op.telemetry.addData("TFOD", "One item detected.");
                        op.telemetry.addData("Target Zone", "B");
                        rings = 1;
                    }

                    // TODO: if there are more than one items in the list find the best one and use it's label
                    if (updatedRecognitions.size() > 1) {
                        op.telemetry.addData("TFOD", "Four items detected.");
                        op.telemetry.addData("Target Zone", "C");
                        rings = 4;
                    }

                    // TODO: can you return both the ring count and the confidence score?
                    //.getConfidence(); replaces .getLabel(); so you know how accurate it thinks it is

                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getBottom()>= 300) {
                            op.telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            op.telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            op.telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());

                            // check label to see which target zone to go after.
                            if (recognition.getLabel().equals("Single")) {
                                op.telemetry.addData("Target Zone", "B");
                                rings = 1;
                            } else if (recognition.getLabel().equals("Quad")) {
                                op.telemetry.addData("Target Zone", "C");
                                rings = 4;
                            } else {
                                op.telemetry.addData("Target Zone", "UNKNOWN");
                            }
                        } else {
                            if(rings == -1) {
                                rings = 0;
                            }
                        }
                    }
                }
            }

            op.telemetry.update();
        }

        return rings;
    }

    public float getConfidence() {
        float conf = 1;
        if (tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                    int i = 0;

                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getBottom() >= 300) {
                            op.telemetry.addData("Confidence: ", recognition.getConfidence());
                            conf = recognition.getConfidence();
                        } else {
                            conf = 1;
                    }
                }
            }
        }
        op.telemetry.update();
        return conf;
    }

}
