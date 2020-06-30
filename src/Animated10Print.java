import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Animated10Print extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main("Animated10Print");
    }

    PImage usedTex;

    private void renderShader(String shaderName, boolean filter) {
        String vignettePass = shaderName + ".glsl";
        uniform(vignettePass).set("time", radians(frameCount));
        if (filter) {
            hotFilter(vignettePass);
        } else {
            hotShader(vignettePass);
        }
    }

    @Override
    public void settings() {
        size(800, 800, P3D);
    }


    private PVector rotate1(PVector origin, PVector point, float angle) {
        float ox = origin.x;
        float oy = origin.y;
        float px = point.x;
        float py = point.y;

        float qx = ox + cos(angle) * (px - ox) - sin(angle) * (py - oy);
        float qy = oy + sin(angle) * (px - ox) + cos(angle) * (py - oy);

        return new PVector(qx, qy);
    }

    private PVector[] rotateLinePoints(PVector start, PVector end, float degrees) {
        float startX = start.x;
        float startY = start.y;
        float endX = end.x;
        float endY = end.y;

        float middleX = floor(startX + endX) / 2f;
        float middleY = floor(startY + endY) / 2f;

        float inRadians = radians(degrees);
        PVector newStart = rotate1((new PVector(middleX, middleY)), start, inRadians);
        PVector newEnd = rotate1((new PVector(middleX, middleY)), end, inRadians);

        return new PVector[]{newStart, newEnd};
    }

    private int counter = 0;

    private List<Line> lines = new ArrayList<>();

    private void initLines() {
        int spacing = 0;
        for (int x = 0; x < width; x += spacing) {
            spacing = (int) random(100);
            for (int y = 0; y < height; y += spacing) {
                if (random(1) > 0.5) {
                    lines.add(new Line(x, y, x + spacing, y + spacing));
                } else {
                    lines.add(new Line(x, y + spacing, x + spacing, y));
                }
            }
        }
    }


    @Override
    public void setup() {
        background(0);
        initLines();
        usedTex = loadImage("fractaltex.png");
    }

    @Override
    public void draw() {
        background(0);
        //stroke(255);
        //renderShader("EffectShader", false);
        counter += 1;
        for (int i = 0; i < lines.size(); i++) {
            float hue = map(i, 0, lines.size(), 55, 165);
            stroke(hue, 100, 77, 99);
            PVector[] lineVectors = rotateLinePoints(new PVector(lines.get(i).x1, lines.get(i).y1), new PVector(lines.get(i).x2, lines.get(i).y2), counter);
            line(lineVectors[0].x, lineVectors[0].y, lineVectors[1].x, lineVectors[1].y);
        }

        //resetShader();


    }
}
