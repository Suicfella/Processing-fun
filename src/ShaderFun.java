import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PImage;


public class ShaderFun extends ShaderSketch {
    public static void main(String[] args) {
        PApplet.main("ShaderFun");
    }

    public void settings() {
        size(800, 800, P3D);
    }

    private PImage tex1, tex2, tex3, imgTex;


    public void setup() {
        background(0);
        surface.setAlwaysOnTop(true);
        surface.setLocation(1920 - 850, 200);
        textureWrap(REPEAT);
        String[] locations = new String[]{"bluetexture.png", "firetexture.png", "fractaltex.png"};
        tex1 = loadImage(locations[0]);
        tex2 = loadImage(locations[1]);
        tex3 = loadImage(locations[2]);
        imgTex = loadImage(locations[2]);
    }

    private void renderShader(String shaderName, boolean filter) {
        String vignettePass = shaderName + ".glsl";
        uniform(vignettePass).set("time", radians(frameCount));
        //  uniform(vignettePass).set("firstTexture", tex1);
        //  uniform(vignettePass).set("secondTexture", tex2);
        // uniform(vignettePass).set("thirdTexture", tex3);
        uniform(vignettePass).set("imgTex", imgTex);
        if (filter) {
            hotFilter(vignettePass);
        } else {
            hotShader(vignettePass);
        }
    }

    private void drawShadedRectangle(int x, int y, int w, int h) {
        //renderShader("textureShader");
        noStroke();
        rect(x, y, w, h);
        resetShader();
    }

    public void draw() {
        translate(width / 2f, height / 2f);
        renderShader("FluidSimulation", true);

    }


}