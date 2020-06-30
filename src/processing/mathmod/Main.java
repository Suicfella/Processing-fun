package processing.mathmod;

import peasy.PeasyCam;
import processing.core.PApplet;

import java.lang.invoke.MethodHandles;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
    }
    PeasyCam cam;
    @Override
    public void setup() {
        background(0);
        colorMode(HSB, 360, 100, 100);
        cam = new PeasyCam(this, 150);
        cam.setMinimumDistance(75);
        cam.setMaximumDistance(1500);

    }

    @Override
    public void draw() {
        background(0);
        translate(width / 2f, height / 2f);
        catenoid();
    }

    private float factor = 0.065f;
    private float scale = 75f;
    private void catenoid() {
        pushMatrix();
        beginShape(POINTS);
       // noFill();
        rotateX(radians(frameCount / 2f));
        rotateY(radians(frameCount / 2f));
        rotateZ(radians(frameCount / 2f));
        strokeWeight(2);
        for (float u = -PI; u <= PI; u += factor) {
            for (float v = -PI; v <= PI; v += factor) {
                float x = (float) (scale * Math.cosh(v / 2) * cos(u));
                float y = (float) (scale * Math.cosh(v / 2) * sin(u));
                float col = map(u, -PI, PI, 0, 360);
                float a = map(v * (scale / 2), -117.80972451f, 117.80972451f, 25, 100);
                stroke(col, a, a);
                vertex(x, y, v * (scale / 2));
            }
        }
        endShape();
        popMatrix();
    }

    @Override
    public void mousePressed() {
     //   factor += 0.01;
    }
}
