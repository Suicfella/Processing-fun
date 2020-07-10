package processing;

import processing.core.PApplet;
import processing.core.PImage;

import java.lang.invoke.MethodHandles;

public class RoseShape extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
        smooth(16);
    }

    private PImage textureImage;
    @Override
    public void setup() {
        background(0);
        textureImage = loadImage("fractaltex.png");
        surface.setAlwaysOnTop(true);
        //colorMode(RGB, 360, 100, 100);
    }

    private float d = 8;
    private float n = 5;

    private int radius = 250;

    private void renderShader(boolean filter, String shaderName) {
        String vignettePass = shaderName + ".glsl";
        uniform(vignettePass).set("time", radians(frameCount));
       // uniform(vignettePass).set("fractalTexture", textureImage);
        if(filter) {
            hotFilter(vignettePass);
        } else {
            hotShader(vignettePass);
        }
    }

    @Override
    public void draw() {
        if (started) {
            float k = n / d;
            background(0);
            translate(width / 2f, height / 2f);

            beginShape();
          //  renderShader(false, "FBMShader");
            //fill(255);
           // noFill();
            noStroke();
           // strokeWeight(4);
            texture(textureImage);
            for (float a = 1; a < TWO_PI * d; a += 0.02) {
                float r = 200 * cos(k * a);
                float x = r * cos(a);
                float y = r * sin(a);
                vertex(x, y, x, y);
            }
            endShape();
           // resetShader();
            n += 0.021;
            d += 0.017;
        }
    }

    private boolean started = false;

    @Override
    public void mousePressed() {
        started = !started;
    }
}
