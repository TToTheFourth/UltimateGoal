package org.firstinspires.ftc.teamcode;

public class RingResult {
    int ringCount;
    float confidence;

    public RingResult(int ringCount, float confidence) {
        this.ringCount = ringCount;
        this.confidence = confidence;
    }

    public int getRingCount() {
        return ringCount;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setRingCount(int ringCount) {
        this.ringCount = ringCount;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }
}
