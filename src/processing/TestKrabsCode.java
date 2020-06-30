package processing;

import processing.core.PApplet;

public class TestKrabsCode extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(TestKrabsCode.class.getName());
    }

    float selectedHue = 0;

    @Override
    public void settings() {
        size(600, 600, P2D);
        smooth(16);
    }

    public void setup() {
        colorMode(HSB, 1, 1, 1, 1);
        smooth(16); // MSAA
    }

    public void draw() {
        background(0);
        translate(width/2, height/2);
        selectedHue = colorPicker();
    }

    public void mousePressed(){
        int selectedColor = color(selectedHue, 1, 1);
        int red = selectedColor >> 16 & 0xff;
        int green = selectedColor >> 8 & 0xff;
        int blue = selectedColor & 0xff;
        println(red(selectedColor)*255, green(selectedColor)*255, blue(selectedColor)*255, selectedHue, selectedColor);
        println(red, green, blue);
    }

    float colorPicker() {

        noStroke();
        float radius = 250;
        int detail = 1000;
        beginShape(TRIANGLE_STRIP);
        for (int i = 0; i < detail; i++) {
            float theta = map(i, 0, detail-1, 0, TAU);
            float hue = getHueAtAngle(theta);
            fill(hue, 1, 1);
            vertex(0, 0);
            vertex(radius*cos(theta), radius*sin(theta));
        }
        endShape();

        float mouseAngle = atan2(mouseY-width/2, mouseX-width/2);
        float selectedHue = getHueAtAngle(mouseAngle);
        fill(selectedHue, 1, 1);
        stroke(0);
        strokeWeight(3);
        rectMode(CENTER);
        rect(0, 0, 50, 50);
        return selectedHue;
    }

    float getHueAtAngle(float angle) {
        while (angle < 0) {
            angle += TAU;
        }
        return map(angle%TAU, 0, TAU, 0, 1);
    }

}
