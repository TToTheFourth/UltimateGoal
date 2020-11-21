package org.firstinspires.ftc.teamcode.visionprograms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.lastYear.teamcode.vision.Directions;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


public class VuHolder {
    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
    float robotx;
    float roboty;
    float roboth;


    VuforiaTrackables targetsSkyStone;
    
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;
    private static final boolean PHONE_IS_UPSIDEDOWN = false;

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

    public VuHolder(LinearOpMode om) {
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

       targetsSkyStone = this.vuforia.loadTrackablesFromAsset("UltimateGoal");

        VuforiaTrackables targetsUltimateGoal = this.vuforia.loadTrackablesFromAsset("UltimateGoal");
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
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsUltimateGoal);


        //Set the position of the perimeter targets with relation to origin (center of field)
        redAllianceTarget.setLocation(OpenGLMatrix
                .translation(0, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        blueAllianceTarget.setLocation(OpenGLMatrix
                .translation(0, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));
        frontWallTarget.setLocation(OpenGLMatrix
                .translation(-halfField, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        // The tower goal targets are located a quarter field length from the ends of the back perimeter wall.
        blueTowerGoalTarget.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));
        redTowerGoalTarget.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

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

        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }
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
            }
        return targetVisible;
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