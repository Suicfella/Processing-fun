import peasy.PeasyCam;
import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PVector;

public class SuperShape extends ShaderSketch {

    // Daniel Shiffman
// http://codingtra.in
// http://patreon.com/codingtrain
// Code for: https://youtu.be/akM4wMZIBWg

    public static void main(String[] args) {
        PApplet.main("SuperShape");
    }

    PeasyCam cam;

    PVector[][] globe;
    int total = 75;


    float m = 0;
    float mchange = 0;

    @Override
    public void settings() {
        size(600, 600, P3D);
    }

    public void setup() {

        cam = new PeasyCam(this, 500);
        colorMode(HSB, 360, 100, 100);
        globe = new PVector[total+1][total+1];
    }

    float a = 1;
    float b = 1;

    float supershape(float theta, float m, float n1, float n2, float n3) {
        float t1 = abs((1/a)*cos(m * theta / 4));
        t1 = pow(t1, n2);
        float t2 = abs((1/b)*sin(m * theta/4));
        t2 = pow(t2, n3);
        float t3 = t1 + t2;
        float r = pow(t3, - 1 / n1);
        return r;
    }
    float offset = 0;
    public void draw() {

       // m = map(sin(mchange), -1, 1, 0, 7);
       // mchange += 0.02;

        background(0);
        noStroke();
        lights();
        offset += 0.05f;
        float r = 200;
        for (int i = 0; i < total+1; i++) {
            float lat = map(i, 0, total, -HALF_PI, HALF_PI);
            float r2 = supershape(lat, 7f, 0.2f, 1.7f, 1.7f);
            //float r2 = supershape(lat, 2, 10, 10, 10);
            for (int j = 0; j < total+1; j++) {
                float lon = map(j, 0, total, -PI, PI);
                float r1 = supershape(lon, 7f, 0.2f, 1.7f, 1.7f);
                //float r1 = supershape(lon, 8, 60, 100, 30);
                float x = r * r1 * cos(lon) * r2 * cos(lat);
                float y = r * r1 * sin(lon) * r2 * cos(lat);
                float z = r * r2 * sin(lat);
                globe[i][j] = new PVector(x, y, z);
            }
        }

        noStroke();
        fill(165, 100, 100);
        //noFill();
        //offset += 5;
        for (int i = 0; i < total; i++) {
           // float hu = map(i, 0, total, 0, 255*6);
           // fill(0, 0, 50 );
            beginShape(TRIANGLE_STRIP);
            for (int j = 0; j < total+1; j++) {
                PVector v1 = globe[i][j];
                vertex(v1.x, v1.y, v1.z);
                PVector v2 = globe[i+1][j];
                vertex(v2.x, v2.y, v2.z);
            }
            endShape();
        }
    }

}
