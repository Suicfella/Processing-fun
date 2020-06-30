package processing;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;

import java.lang.invoke.MethodHandles;

public class SomethingFun extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 800, P3D);
        smooth(16);
    }


    private PGraphics pg;
    @Override
    public void setup() {
        background(0);
        pg = createGraphics(width, height, P3D);
        pg.translate(pg.width / 2f, pg.height / 2f);
        ellipseSphere();

        colorMode(HSB, 360, 100, 100);
    }

    private int r = 200;
    private int iterations = 0;

    private void renderSphere(float r, int detail) {

        float dphi = TWO_PI / detail; // change in phi angle
        float dtheta = PI / detail; // change in theta angle

        // process the sphere one band at a time
        // going from almost south pole to almost north
        // poles must be handled separately
        float theta2 = -PI/2+dtheta;
        float z2 = sin(theta2); // height off equator
        float rxyUpper = cos(theta2); // closer to equator
        for (int i = 1; i < detail; i++) {
            float theta1 = theta2;
            theta2 = theta1 + dtheta;
            float z1 = z2;
            z2 = sin(theta2);
            float rxyLower = rxyUpper;
            rxyUpper = cos(theta2); // radius in xy plane
            stroke(255);
            for (int j = 0; j <= detail; j++) {
                float phi = j * dphi; //longitude in radians
                float xLower = rxyLower * cos(phi);
                float yLower = rxyLower * sin(phi);
                float xUpper = rxyUpper * cos(phi);
                float yUpper = rxyUpper * sin(phi);
                pushMatrix();
                translate(0, 0, r * z1);
                ellipse(r*xLower, r*yLower, 15, 15);
                translate(0, 0, r * z2);
                ellipse(r*xUpper, r*yUpper, 15, 15);
                popMatrix();
            }
        }
    }

    public float angularDiameter(float r, float size) {
        return atan(2 * (size / (2 * r)));
    }

    private void ellipseSphere() {
        pg.beginDraw();
        pg.translate(pg.width / 2f, pg.height / 2f);
        pg.noFill();
        pg.stroke(255);
        System.out.println("Yes rendering");
        float r = 220;
        float step = 8;
      //  lightSpecular(0, 255, 0);
        pointLight(255, 0, 0, 0, 0, 0);
     //   ambientLight(255, 0, 0);
        float rowAngularDiameter = angularDiameter(r, step);
        int rowCount = floor(PI / rowAngularDiameter);
        for (int i = 0; i < rowCount; i++) {
            //go from north pole to south pole
            float polarAngle = map(i, 0, rowCount - 1, -HALF_PI, HALF_PI); // wikipedia also calls this inclination
            // to find the radius at this latitude I simplify the sphere to a circle where the result is the x at this polar angle and radius
            float latRadius = r * cos(polarAngle);
            float columnAngularStep = angularDiameter(latRadius, step);
            int columnCount = max(1, floor(TWO_PI / columnAngularStep)); //at least 1 column should be shown at poles
            for (int j = 0; j <= columnCount; j++) {
                //go all the way around the sphere
                float columnAngle = map(j, 0, columnCount, 0, TWO_PI); // wikipedia also calls this azimuth
                pg.pushMatrix();
                pg.rotateY(columnAngle);
                pg.rotateZ(polarAngle);
                pg.translate(r, 0, 0);
                pg.rotateY(HALF_PI);
                float hue = map(i, 0, rowCount, 0, 360);
             //   pg.stroke(hue, 100, 100);

                pg.ellipse(0, 0, noise(step) * 32, noise(step));
              //  pg.line(0, 0, noise(step), noise(step));
                pg.popMatrix();
            }
        }
        pg.endDraw();
    }

    @Override
    public void draw() {
        background(0);
        loadPixels();
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = color(175, random(5, 25), 5);
        }
        updatePixels();
      //  image(pg, 0, 0);

        /*if (mousePressed) {
           // iterations = (int) map(mouseX, 0, width, 1, 500);
            iterations++;
        }
        renderSphere(200, iterations);*/
       /* for (int i = 0; i < iterations; i++) {
            int r = (int) map(i, 0, iterations, 500, 1);
            int alpha = (int) map(i, 0, iterations, 15, 125);
            stroke(r % 360, r % 100, r % 100, alpha);
            ellipse(r, r / 1.6f, r, r);
        }*/

    }

    private void recursiveRings(int iterations, int count, int offset, int radius) {
        if (count < 1) {
            System.out.println("Finished");
            return;
        }
        pushMatrix();
        noFill();
        stroke(255);
        translate(width / 2f, 100);
        for (int i = 0; i < iterations; i++) {
            int x = (int) (r * cos(i));
            int y = (int) (r * sin(i));
            ellipse(x, y + offset, radius, radius);
        }
        popMatrix();
        offset += 150;
        recursiveRings(iterations, count - 1, offset, radius);

    }


}
