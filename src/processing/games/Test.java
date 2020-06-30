package processing.games;

import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.lang.invoke.MethodHandles;


public class Test extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(600, 600, P3D);
    }

    private PShape torus;

    @Override
    public void setup() {
        background(0);
        colorMode(HSB, 360, 100, 100, 100);
        torus = createMeshTorus(mouseX, 75, 25, 25);
    }

    int P = 3;
    int Q = 2;
    final float A = 5;
    float angle = 0f;

    @Override
    public void draw() {
        background(0);
        translate(width / 2f, 100);
        noFill();
        stroke(255);
        rotate(angle);
        for (float phi = 0; phi < Q * TAU; phi += 0.035f) {
            float r = cos(Q * phi) + 2;
            float x = A * (r * cos(P * phi));
            float y = A * r * sin(P * phi);
            float z = -A * sin(Q * phi);

            pushMatrix();
            translate(x, y, z);

            sphere(25);

            popMatrix();

        }


        angle += 0.01;
    }

    public PShape createMeshTorus(float r1, float r2, int divX, int divY) {

        PShape mesh = createShape(GROUP);

        float latheAngle = 0;
        float angle = 0;

        PVector[] vertices = new PVector[divX + 1];
        PVector[] vertices2 = new PVector[divX + 1];

        //DATA
        for (int i = 0; i <= divX; i++) {
            vertices[i] = new PVector();
            vertices2[i] = new PVector();
            vertices[i].x = r1 + sin(radians(angle)) * r2;

            //TORUS Z
            vertices[i].z = cos(radians(angle)) * r2;
            angle += 360.0 / divX;
        }

        //CREATE
        latheAngle = 0;
        for (int i = 0; i <= divY; i++) {
            PShape child = createShape();
            child.beginShape(QUAD_STRIP);
            float color = map(i, 0, divY, 0, 360);
            child.noFill();
            float mappedAlpha = map(mouseY, 0, height, 1, 35);
            child.stroke(170, 100, 100, 25);
            for (int j = 0; j <= divX; j++) {
                if (i > 0) {
                    child.vertex(vertices2[j].x, vertices2[j].y, vertices2[j].z);
                }
                vertices2[j].x = cos(radians(latheAngle)) * vertices[j].x;
                vertices2[j].y = sin(radians(latheAngle)) * vertices[j].x;
                vertices2[j].z = vertices[j].z;
                child.vertex(vertices2[j].x, vertices2[j].y, vertices2[j].z);
            }
            //DEFAULT ROTATION
            latheAngle += 360.0 / divY;

            child.endShape();
            mesh.addChild(child);
        }

        return mesh;
    }

}