package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Gyro2 {

    private double angle = 0;
    private double last = 0;
    BNO055IMU gyro;

    public Gyro2(BNO055IMU gyro) {
        this.gyro = gyro;
    }

    public void reset() {
        angle = 0;
        last = read();
    }

    private double read() {
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }

    public double getAngle() {
        double current = read();

        if(last >= 0 && current >= 0) {
            angle += current - last;
        } else if(last < 0 && current < 0) {
            angle += current - last;
        } else if(last >= 90 && current <= -90) {
            angle += current + 180 + 180 - last;
        } else if(last >= 0 && last < 90 && current > -90 && current < 0) {
            angle -= last - current;
        } else if(current >= 90 && last <= -90) {
            angle -= last + 180 + 180 - current;
        } else if (current >= 0 && current < 90 && last > -90 && last < 0) {
            angle += current - last;
        } else {
        }
        last = current;

        return angle;
    }
}
