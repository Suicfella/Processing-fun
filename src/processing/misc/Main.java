package processing.misc;

import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PFont;


import java.lang.invoke.MethodHandles;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Main extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(720, 720, P3D);
        smooth(16);
    }

    PFont font;

    @Override
    public void setup() {
        background(0);
        frameRate(60);
        font = createFont("Arial", 14);
        textFont(font);
        colorMode(HSB, 360, 100, 100);
    }

    private int screenDimension = 720;
    private double sphereRadius = screenDimension * 0.4;
    private int surfaceThickness = 1;
    private float yRotateRadiansPerMillisecond = 0.0004f;
    private int pointsToAddEachFrame = 1000;
    private NumberFormat fmt = NumberFormat.getIntegerInstance();
    private List<Point> points = new ArrayList<>();
    private int numPointsGenerated = 0;
    private long startTimeMillis = millis();
    private Point centerPoint = new Point(0, 0, 0);

    @Override
    public void draw() {
        addBatchOfPoints();
        background(0);

        pushMatrix();
        translate(width / 2f, height / 2f, 0);
        rotateY(yRotateRadiansPerMillisecond * (millis() - startTimeMillis));

        points.forEach(p -> {
            stroke(155 + random(40) - random(40), 100, 100);
            point(p.x, p.y, p.z);
        });
        popMatrix();

        int textY = height - 10;
        text("Points generated: " + fmt.format(numPointsGenerated), 10, textY);
        text("Points on “surface”: " + fmt.format(points.size()), 220, textY);
        text("Frames/second: " + (int) frameRate, 420, textY);

    }

    public float distanceTo(Point p) {
        return sqrt(pow(p.x, 2) + pow(p.y, 2) + pow(p.z, 2));
    }

    public float getDistance(Point p1, Point p2) {
        return sqrt(pow(p1.x - p2.x, 2) + pow(p1.y - p2.y, 2) + pow(p1.z - p2.z, 2));
    }

    private int randCoord() {
        return (int) (Math.random() * screenDimension) - screenDimension / 2;
    }

    private void addBatchOfPoints() {
        yRotateRadiansPerMillisecond *= 1.001;
        for (int i = 1; i <= pointsToAddEachFrame; i++) {
            numPointsGenerated++;
            Point p = new Point(randCoord(), randCoord(), randCoord());
            double radiusThisPoint = this.getDistance(centerPoint, p);
            boolean pointWithinSurfaceThickness =
                    radiusThisPoint >= sphereRadius - surfaceThickness && radiusThisPoint <= sphereRadius;
            if (pointWithinSurfaceThickness) {
                points.add(p);
            }
        }
    }

    class Point {

        public int x, y, z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

class Point {

    public int x, y, z;
    public double surfaceCloseness;

    public Point(int x, int y, int z, double surfaceCloseness) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.surfaceCloseness = surfaceCloseness;
    }
}


