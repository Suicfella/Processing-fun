package processing;

import processing.core.PApplet;

import java.lang.invoke.MethodHandles;

public class WillItCrash extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(1300, 890, P3D);
        smooth(16);
    }

    private final float R = 200;
    private float d = 200;
    private float r = 25;

    private float theta = 0f;
    private int length = 0;

    @Override
    public void setup() {
        background(0);
        colorMode(HSB, 360, 100, 100);
        // blendMode(ADD);
    }


    @Override
    public void draw() {

        if (started) {
            background(0);
            stroke(255);
            text("Length: " + length, 100, 100);
            translate(width / 2f, height / 2f);
            strokeWeight(5);
            beginShape();
            noFill();
            float maxLength = 1000;
            for (int i = 0; i < length; i++) {
                theta = map(i, 0, maxLength, 0, TAU * 5);
                float x = x(theta);
                float y = y(theta);
                float brightness = 100 * norm(i, 0, maxLength);
                stroke(i % 360, brightness, 100);
                vertex(x, y);
            }
            endShape();
            length += 25;
        }

    }

    private boolean started = false;

    @Override
    public void mousePressed() {
        started = !started;
        r += 5;
    }

    private float x(float theta) {
        return (R - r) * cos(theta) + d * cos((R - r) / r * theta);
    }

    private float y(float theta) {
        return (R - r) * sin(theta) - d * sin((R - r) / r * theta);
    }
}
