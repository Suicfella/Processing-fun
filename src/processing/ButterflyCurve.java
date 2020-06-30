package processing;

import processing.core.PApplet;
import processing.core.PVector;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class ButterflyCurve extends ShaderSketch {

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
        colorMode(HSB, 360, 100, 100);

        points = new ArrayList<>();
        for(float theta = 0; theta < MAX_THETA; theta += 0.001) {
            float r = exp(cos(theta)) - 2 * cos(4 * theta) + pow(sin(theta / 24f), 5);
            float x = r * cos(theta);
            float y = r * sin(theta);
            float xx = (x * 60);
            float yy = (y * 60);
            float col = map(theta, 0, MAX_THETA, 0, 360);
            points.add(new Point(xx, yy, col));
        }
    }

    private float theta = 0f;
    private final float MAX_THETA = PI * 12;

    private List<Point> points;

    @Override
    public void draw() {
        background(0);
        //stroke(255);
        strokeWeight(2);
        translate(width / 2f, height / 2f);



        noFill();
        for (int i = 0; i < points.size(); i++) {
            beginShape();
            Point p = points.get(i);

            stroke(p.col, 80, 80, 5);
            vertex(p.x, p.y);
            endShape();
        }

        System.out.println("Drawing");


    }

    class Point {
        public float x, y, col;

        public Point(float x, float y, float col) {
            this.x = x;
            this.y = y;
            this.col = col;
        }
    }


}
