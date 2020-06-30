import processing.ShaderSketch;
import processing.core.*;

public class ShadersAreFun extends ShaderSketch {
    public static void main(String[] args) {
        PApplet.main("ShadersAreFun");
    }





    public void settings() {
        size(1200, 600, P3D);
    }
    PImage textureImage;







    void drawTriangle(float x, float y, float w, float h) {
        // A wrapper for standard triangle() method.
        // drawTriangle has the lower left corner as x,y
       // vignettePass1();
        triangle(x, y,
                x+w/2, y-h,
                x+w, y);

        //resetShader();
    }
    private void drawEllipse(int x, int y, int radius) {
        //noStroke();
        //vignettePass2();
        ellipse(x, y, radius, radius);
        //resetShader();
    }

    private void drawCircle(int x, int y, int extent) {
        noStroke();

    }

    PShape torus;

    PGraphics tex;

    public void setup() {
        //surface.setAlwaysOnTop(true);
        //surface.setLocation(1920-850, 200);
        textureWrap(REPEAT);
        textureImage = loadImage("firetexture.png");
        torus = createTorus(195, 80, 125, 125);
        tex = createGraphics(100, 100); // unused
    }

    protected void renderShader2(boolean filter, String shaderName) {
        String vignettePass = shaderName + ".glsl";
        uniform(vignettePass).set("time", radians(frameCount));
        uniform(vignettePass).set("fractalTexture", textureImage);
        if(filter) {
            hotFilter(vignettePass);
        } else {
            hotShader(vignettePass);
        }
    }

    protected void renderShader1(boolean filter, String shaderName) {
        String vignettePass = shaderName + ".glsl";
        uniform(vignettePass).set("time", radians(frameCount));
        uniform(vignettePass).set("mainTexture", textureImage);
        if(filter) {
            hotFilter(vignettePass);
        } else {
            hotShader(vignettePass);
        }
    }

    float r2 = 0f;
    float angle = 0;
    public void draw() {


    }
    boolean looping = true;
    public void mousePressed() {
        //looping = !looping;
        setup();
        //if(looping) noLoop(); else loop();
    }

    public PShape createTorus(float r1,float r2, int divX, int divY) {

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

    protected void vignettePass(){
        String vignettePass = "fragmentShader.glsl";
        uniform(vignettePass).set("time", radians(frameCount));
        hotFilter(vignettePass);
    }

    protected void vignettePass1(){
        String vignettePass = "FBMShader.glsl";
        uniform(vignettePass).set("time", radians(frameCount));
        hotShader(vignettePass);
    }


}