import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PImage;


public class ProcessingTest extends PApplet {
    public static void main(String[] args) {
        PApplet.main(java.lang.invoke.MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
    }
    private PImage mandelbrot;
    private PeasyCam cam;
    @Override
    public void setup() {
        background(0);
        imageMode(CENTER);
        cam = new PeasyCam(this, 777);
        cam.setMinimumDistance(50);
        cam.setMaximumDistance(500);
        mandelbrot = loadImage("mandelbrot.jpeg");
    }

    @Override
    public void draw() {
        background(0);
        noStroke();
        fill(255, 125);
        translate(width / 2f, height / 2f, -50);
        image(mandelbrot, 0, 0);
    }
}
