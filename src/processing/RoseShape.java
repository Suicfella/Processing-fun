package processing;

import processing.core.PApplet;

import java.lang.invoke.MethodHandles;

public class RoseShape extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600);
        smooth(16);
    }

    @Override
    public void setup() {
        background(0);
        //colorMode(RGB, 360, 100, 100);
    }

    private float k = 0;
    private float d = 0;
    private int radius = 250;

    @Override
    public void draw() {
        if (started) {
            background(0);


            strokeWeight(3);
            noFill();
            translate(width / 2f, height / 2f);
            for (float t = 0; t < TAU * d; t += 0.006) {
                float r = radius * cos(k * t);
                float x = r * cos(t);
                float y = r * sin(t);
                beginShape();
                float col = map(t, 0, TAU * d, 0, 255);
                stroke(col, col, col, 75);
                vertex(x, y);
                endShape(CLOSE);
            }


            k += 0.004;
            d += 0.007;

        }
    }

    private boolean started = false;

    @Override
    public void mousePressed() {
        started = !started;
    }
}
