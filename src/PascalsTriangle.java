import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PFont;

public class PascalsTriangle extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main("PascalsTriangle");
    }

    @Override
    public void settings() {
        size(700, 700, P2D);
    }

    PFont f;
    @Override
    public void setup() {
        background(0);
        f = createFont("Arial", 10, true);
        textAlign(CENTER, CENTER);
        surface.setAlwaysOnTop(true);
        surface.setLocation(1920-850, 200);
       // frameRate(5);
    }

    protected void renderShader1(boolean filter, String shaderName) {
        String vignettePass = shaderName + ".glsl";
        uniform(vignettePass).set("time", radians(frameCount));
        if(frameCount % 60 == 0) {
            uniform(vignettePass).set("red", random(0.1f, 0.9f));
            uniform(vignettePass).set("green", random(0.4f, 0.8f));
            uniform(vignettePass).set("blue", random(0.1f, 0.7f));
        }
        if(filter) {
            hotFilter(vignettePass);
        } else {
            hotShader(vignettePass);
        }
    }



    void drawHexagon(float x, float y, float s) {
        beginShape();
        //stroke(255);
        colorMode(HSB, 360, 100, 100);
        stroke(123, 90, 97);
        vertex(x - sqrt(3)*s/2, y + s/2);
        stroke(300, 100, 100);
        vertex(x, y+s);
        stroke(100, 80, 80);
        vertex(x + sqrt(3)*s/2, y + s/2);
        stroke(150, 80, 80);
        vertex(x + sqrt(3)*s/2, y - s/2);
        stroke(200, 80, 80);
        vertex(x, y-s);
        stroke(250, 80, 80);
        vertex(x - sqrt(3)*s/2, y - s/2);
        stroke(300, 80, 80);
        vertex(x - sqrt(3)*s/2, y + s/2);
        stroke(350, 80, 80);
        endShape();
    }
    float count = 1;
    private final String[] SHADERS = {"EffectShader", "FBMShader"};
    String shader = "EffectShader";

    float angle = 0f;
    @Override
    public void draw() {
        background(0);
        beginShape();
        fill(255, 255, 255, 0);
        translate(15, 0);
        stroke(0, 0, 0);
        float s = 20;
        angle += 0.01f;
        angle = angle % 0.1f;
        if(frameCount % 10 == 0) {
            count++;
        }
        if(count > 19) {
            count = 19;
        }
        textFont(f, 10);

        //fill(0);
      //  renderShader1(false, shader);
        for (int r = 1; r < count; r++) {
            long b = 1L;

            for (int c = 0; c < r; c++) {
                fill(0, 0, 0, 0);
                float x = 350 - s * sqrt(3) / 2 * (r - c * 2);
                float y = s / 2 + r * 3 * s / 2;
                drawHexagon(x, y, 20);
                colorMode(HSB, 360, 100, 100);
                float col = map(r, 0, 20, 0, 360);
                fill(col);
                text(floor(b), x, y);
                b = (r-c-1)*b/(c+1);
            }

        }

      //  resetShader();
    }
}
