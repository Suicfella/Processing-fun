package processing;

import processing.core.PApplet;
import processing.core.PGraphics;

import java.lang.invoke.MethodHandles;

public class LogSpiral extends ShaderSketch {
    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600);
    }


    @Override
    public void setup() {
        background(0);
        colorMode(HSB, 360, 100, 100);
    }

    int xSpacing = 3;
    int ySpacing = 3;
    int x = 0;
    int y = 0;
    int count = 0;

    @Override
    public void draw() {
        background(0);

        x = 0;
        y = 0;
        for (int i = 0; i < count; i++) {
            float col = map(i, 0, count, 0, 255);
            stroke(255, col);
            line(x, y, i * count, y);
            y += ySpacing;
        }
        if (frameCount % 4 == 0)
            count += 10;

    }
}

