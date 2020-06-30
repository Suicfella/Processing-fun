import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PVector;

public class PrismSketch extends ShaderSketch {


    public static void main(String[] args) {
        PApplet.main("PrismSketch");
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
    }

    @Override
    public void setup() {
        background(0);
    }

    private PVector getPV(float x, float y, float z) {
        return new PVector(x, y, z);
    }

    private final float PHI = (1 + sqrt(5)) / 2;
    private final float H = sqrt(sqrt(11 * PHI + 7) - 2 * PHI - 1);
    private final PVector P1 = getPV(2 * PHI, 0, H);
    private final PVector P2 = getPV(-2 * PHI, 0, H);

    private final PVector[] POINTS = {P1, P2};

    @Override
    public void draw() {
        background(0);
        translate(width / 2f, height / 2f);
        beginShape(TRIANGLES);
        stroke(255);
        strokeWeight(10);
        for (PVector point : POINTS) {
            vertex(point.x, point.y, point.z);
        }
        endShape();
    }
}
