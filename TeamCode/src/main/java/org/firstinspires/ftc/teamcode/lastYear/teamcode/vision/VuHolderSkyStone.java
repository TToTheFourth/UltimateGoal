package org.firstinspires.ftc.teamcode.lastYear.teamcode.vision;

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

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


public class VuHolderSkyStone {
    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
    float robotx;
    float roboty;
    float roboth;


    VuforiaTrackables targetsSkyStone;

    // IMPORTANT:  For Phone Camera, set 1) the camera source and 2) the orientation, based on how your phone is mounted:
    // 1) Camera Source.  Valid choices are:  BACK (behind screen) or FRONT (selfie side)
    // 2) Phone Orientation. Choices are: PHONE_IS_PORTRAIT = true (portrait) or PHONE_IS_PORTRAIT = false (landscape)
    //
    // NOTE: If you are running on a CONTROL HUB, with only one USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    //
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;
    private static final boolean PHONE_IS_UPSIDEDOWN = true;

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

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;
    private LinearOpMode opMode;
    private VuforiaTrackable stoneTarget;

    public VuHolderSkyStone(LinearOpMode om) {
        opMode = om;
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;

        // new! this is very important!!
        parameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.

       targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables.addAll(targetsSkyStone);

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

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2.setLocation(OpenGLMatrix
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
            phoneXRotate = 90 ;
        } else if (PHONE_IS_UPSIDEDOWN) {
            phoneXRotate = 270 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 7.5f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 5.75f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = -5.0f * mmPerInch;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        // WARNING:
        // In this sample, we do not wait for PLAY to be pressed.  Target Tracking is started immediately when INIT is pressed.
        // This sequence is used to enable the new remote DS Camera Preview feature to be used with this sample.
        // CONSEQUENTLY do not put any driving commands in this loop.
        // To restore the normal opmode structure, just un-comment the following line:

        //targetsSkyStone.activate();
    }

    public boolean check() {

            // check all the trackable targets to see which one (if any) is visible.
            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    targetVisible = true;
                    //opMode.telemetry.addData("Visible Target", trackable.getName());
                    // opMode.telemetry.update();

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
                //float x = translation.get(0) / mmPerInch;
//                opMode.telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
//                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

//                opMode.telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
//                        translation.get(0), translation.get(1), translation.get(2));


                float x = translation.get(0) / mmPerInch;
                float y = translation.get(1) / mmPerInch;
                float z = translation.get(2) / mmPerInch;

                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                //opMode.telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);

                float roll = rotation.firstAngle;
                float pitch = rotation.secondAngle;
                float heading = (float) Math.toRadians(rotation.thirdAngle);

                robotx = x;
                roboty = y;
                roboth = heading; // + (float)Math.PI;

//                opMode.telemetry.addData("x", robotx);
//                opMode.telemetry.addData("y", roboty);
//                opMode.telemetry.update();
            }
            //opMode.telemetry.update();

//        float distance = getDistance(xtarget, ytarget, xrobot, yrobot);
//        float rotate = this.getDirection(xtarget, ytarget, xrobot, yrobot, hrobot, distance);

//        if (rotate > 0) {
//            bot.turnLeft(-rotate, 0.25);
//        } else if (rotate < 0) {
//            bot.turnRight(rotate, 0.25);
//        }
//        bot.goForward(0.5, distance);
//        //todo: DOES THIS WORK?
//
//        // Disable Tracking when we are done;
//        targetsSkyStone.deactivate();

        return targetVisible;
    }
    public boolean checkForSkystone() {

        // check all the trackable targets to see which one (if any) is visible.
        boolean stoneTargetVisible = false;
            if (((VuforiaTrackableDefaultListener) stoneTarget.getListener()).isVisible()) {
                stoneTargetVisible = true;
                //opMode.telemetry.addData("Visible Target", trackable.getName());
                // opMode.telemetry.update();

                // getUpdatedRobotLocation() will return null if no new information is available since
                // the last time that call was made, or if the trackable is not currently visible.
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) stoneTarget.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
            }

        // Provide feedback as to where the robot is located (if we know).
        if (stoneTargetVisible) {
            // express position (translation) of robot in inches.
            VectorF translation = lastLocation.getTranslation();
            //float x = translation.get(0) / mmPerInch;
//                opMode.telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
//                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

//                opMode.telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
//                        translation.get(0), translation.get(1), translation.get(2));


            float x = translation.get(0) / mmPerInch;
            float y = translation.get(1) / mmPerInch;
            float z = translation.get(2) / mmPerInch;

            // express the rotation of the robot in degrees.
            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            //opMode.telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);

            float roll = rotation.firstAngle;
            float pitch = rotation.secondAngle;
            float heading = (float) Math.toRadians(rotation.thirdAngle);

            robotx = x;
            roboty = y;
            roboth = heading; // + (float)Math.PI;
        }
        return stoneTargetVisible;
    }

    public void activate() {
        targetsSkyStone.activate();
    }
    public void deactivate() {
        targetsSkyStone.deactivate();
    }
    public float xCoor() {
        float xCoor = robotx;
        return xCoor;
    }
    public float yCoor() {
        float yCoor = roboty;
        return yCoor;
    }
    public float heading() {
        return roboth;
    }
    public Directions getDirections(float x, float y) {
        Directions directions = new Directions();
        float distance = getDistance(x, y, robotx, roboty);
        float heading = getDirection(x, y, robotx, roboty, roboth, distance);
        directions.setDistance(distance);
        directions.setHeading(heading);
        return directions;
    }
    public float getDirection(float x, float y, float xr, float yr, float heading, float distance) {
        double h = (double) heading;
        double x1 = (double) xr;
        double y1 = (double) yr;
        double x2 = (double) x;
        double y2 = (double) y;
        double distanceOG = (double) distance;
        float theta = 0;
        if (x2 - x1 == 0) {
            if (y2 - y1 >= 0) {
                theta = (float) (( Math.PI / 2) - h);
            } else {
                theta = (float) (h - (Math.PI / 2));
            }
        } else {
            double m1 = (y2 - y1) / (x2 - x1);
            double m2 = Math.tan(h);
            theta = (float) Math.atan(((m1 - m2) / (1 + m1 * m2)));
        }
        double newTheta = theta + h;
        double xt = (float) Math.cos(newTheta) + xr;
        double yt = (float) Math.sin(newTheta) + yr;
        double dist = Math.sqrt(Math.pow(xt - x, 2) + Math.pow(yt - y, 2));
        if (distanceOG > dist) {
            theta = theta;
        }else{
            theta = (float) (Math.PI + theta);
        }
        if (theta > Math.PI) {
            theta = (float) (theta - 2.0 * Math.PI);
        } else if (theta < -Math.PI) {
            theta = (float) (theta + 2.0 * Math.PI);
        }

        return theta;
    }
    public float getDistance(float xp, float yp, float xr, float yr) {
        double xp1 = (double) xp;
        double yp1 = (double) yp;
        double xr1 = (double) xr;
        double yr1 = (double) yr;
        double length = Math.sqrt(Math.pow(xp1 - xr1, 2) + Math.pow(yp1 - yr1, 2));
        return (float) length;
    }
}