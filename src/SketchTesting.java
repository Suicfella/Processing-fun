import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;


public class SketchTesting extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main("SketchTesting");
    }


    PImage usedTex;

    private void renderShader(String shaderName, boolean filter) {
        String vignettePass = shaderName + ".glsl";
        uniform(vignettePass).set("time", radians(frameCount));
        uniform(vignettePass).set("usedTex", usedTex);
        if (filter) {
            hotFilter(vignettePass);
        } else {
            hotShader(vignettePass);
        }
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
    }

    public PShape createTorus(float r1, float r2, int divX, int divY) {

        PShape mesh = createShape(GROUP);

        float latheAngle = 0;
        float angle = 0;

        PVector[] vertices = new PVector[divX+1];
        PVector[]  vertices2 = new PVector[divX+1];

        //data
        for(int i=0; i<=divX; i++){
            vertices[i] = new PVector();
            vertices2[i] = new PVector();
            vertices[i].x = r1 + sin(radians(angle))*r2;

            //torus z axis
            vertices[i].z = cos(radians(angle))*r2;
            angle+=360.0/divX;
        }

        //create shape
        latheAngle = 0;
        for(int i=0; i<=divY; i++){
            PShape child = createShape();
            child.beginShape(QUAD_STRIP);
            child.noStroke();
            // child.stroke(65,0,125, 160);
            for(int j=0; j<=divX; j++){
                if (i>0){
                    child.vertex(vertices2[j].x, vertices2[j].y, vertices2[j].z);
                }
                vertices2[j].x = cos(radians(latheAngle))*vertices[j].x;
                vertices2[j].y = sin(radians(latheAngle))*vertices[j].x;
                vertices2[j].z = vertices[j].z;
                child.vertex(vertices2[j].x, vertices2[j].y, vertices2[j].z);
            }
            // just default rotation
            latheAngle += 360.0/divY;

            child.endShape();
            mesh.addChild(child);
        }

        return mesh;
    }

    PShape torus;

    @Override
    public void setup() {
        background(0);
        usedTex = loadImage("fractaltex.png");
        surface.setAlwaysOnTop(true);
        surface.setLocation(1920 - 850, 200);

    }
    float angle = 0;
    @Override
    public void draw() {
        background(0);
        translate(width / 2f, height / 2f);
        renderShader("EffectShader", false);
        rotateX((PI / 2) - angle);
        rotateY((PI / 4) + angle);
        rotateZ(angle / 4f);
        pushMatrix();
        stroke(255);
        box(275);
        popMatrix();
        angle += 0.01;

        resetShader();

    }
}
