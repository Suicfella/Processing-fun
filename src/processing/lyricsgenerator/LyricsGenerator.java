package processing.lyricsgenerator;

import processing.ShaderSketch;
import processing.core.PApplet;
import java.lang.invoke.MethodHandles;

public class LyricsGenerator extends ShaderSketch {
    public static void main(String[] args) {
       PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
        smooth(16); // Anti aliasing
    }

    @Override
    public void setup() {
        background(0);
    }

    @Override
    public void draw() {
        background(0);
        noStroke();
        fill(255, 0, 0, 125);
        ellipse(width / 2f, height / 2f, 100, 100);

    }
}
