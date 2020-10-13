package org.firstinspires.ftc.teamcode.lastYear.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
public class Directions {
    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        this.heading = heading;
    }

    float distance;
    float heading;
}
