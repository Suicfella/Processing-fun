import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class MaurerRose extends PApplet {
    PeasyCam cam;
    public static void main(String[] args) {
        PApplet.main("MaurerRose");
    }

    public void settings() {
        size(800, 800);
        smooth(8);
    }

   public void setup() {

    }

    float n = 6f;
    float d = 71f;
    float hueColor = 0f;
   public void draw() {
        background(0);
        translate(width/2, height/2);
        stroke(255);
        noFill();
        beginShape();
        strokeWeight(1);
        for (float i = 0; i <= TWO_PI; i += TWO_PI/ 360) { // degrees to radians
            float k = i * d;
            float radius = 150 * sin(n * k);
            float x = radius * cos(k);
            float y = radius * sin(k);

            vertex(x, y);
        }
        endShape();

        noFill();
        colorMode(HSB);
        hueColor += 1;
        if(hueColor > 360) {
            hueColor = 0;
        }
        stroke(hueColor, 100, 100, 155);
        strokeWeight(4);
        beginShape();
        for (float i = 0; i <= TWO_PI; i += TWO_PI/ 360) { // degrees to radians
            float k = i * d;
            float radius = 150 * sin(n * k);
            float x = radius * cos(k);
            float y = radius * sin(k);

            vertex(x, y);

            //println("I: " + i);
        }
        endShape(CLOSE);
            //d += random(0.0001f, 0.0003f);
       // System.out.println("D is: " + d);
    }
}