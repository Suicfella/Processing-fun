package processing;

import peasy.PeasyCam;
import processing.core.PApplet;

import java.lang.invoke.MethodHandles;

public class SaddleSurface extends ShaderSketch {

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
        cam = new PeasyCam(this, 100);
        cam.setMinimumDistance(50);
        cam.setMaximumDistance(1000);
        colorMode(HSB, 360, 100, 100);
    }

    private float a = 15;
    private float b = 15;
    private float max = 15;


    @Override
    public void draw() {
        background(0);

        //fill(255);
        noFill();
        translate(width / 2f, height / 2f);
        //scale(15);
        beginShape(TRIANGLE_STRIP);

        for (float x = -max; x < max; x += 1) { // max is 1
            for (float z = -max; z < max; z += 1) {
                float col = map(x, -max, max, 0, 360);
                stroke(col, 85, 85);
                vertex(x, x * x - z * z, z);
            }
        }
        endShape();

        //// trying new a, b values
        //a -= 0.05;
      //  b -= 0.05;


    }


}
