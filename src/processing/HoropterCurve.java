package processing;

import peasy.PeasyCam;
import processing.core.PApplet;

import java.lang.invoke.MethodHandles;

public class HoropterCurve extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
        smooth(16);
    }

    private PeasyCam cam;

    @Override
    public void setup() {
        background(0);
        cam = new PeasyCam(this, 100);
        cam.setMinimumDistance(-1000);
        cam.setMaximumDistance(2500);
        colorMode(HSB, 360, 100, 100);
    }

    float a = 3;
    float b = 2;
    private int r = 100;
    private float angle = 0f;

    @Override
    public void draw() {
        background(0);

        strokeWeight(10);
        //  float fov = PI / 2.0f;
        // float cameraZ = (height / 2.0f) / tan(fov / 2.0f);

        translate(width / 2f, height / 3f);


        //rotateZ(angle);
        for (float t = 0; t < TAU; t += 0.005) {

            float x1 = a * cos(t);
            float y1 = b * sin(t);
            float z1 = 0;
            x1 *= r;
            y1 *= r;
            z1 *= r;

            float x2 = b * cos(t);
            float y2 = 0;
            float z2 = a * sin(t);

            x2 *= r;
            y2 *= r;
            z2 *= r;

            float x3 = 0;
            float y3 = a * cos(t);
            float z3 = b * sin(t);

            x3 *= r;
            y3 *= r;
            z3 *= r;
            float col = map(t, 0, TAU, 0, 360);
            stroke(col, 85, 85);
            point(x1, y1, z1);
            stroke(col, 85, 85);
            point(x2, y2, z2);
            stroke(col, 85, 85);
            point(x3, y3, z3);

            System.out.println(x1 + " | " + y1 + " | " + z1 + " | " + x2 + " | " + y2 + " | " + z2 + " | " + x3 + " | " + y3 + " | " + z3);
        }

        angle += 0.03;

        // endShape();

        //  a += 0.35;
        //b += 0.73;

    }

    @Override
    public void mousePressed() {

    }
}
