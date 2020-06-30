import processing.core.PApplet;

public class HelloProcessing extends PApplet {
    public static void main(String[] args) {
        PApplet.main("HelloProcessing");
    }

    public void settings() {
        size(800, 800, P2D);
    }

    public void setup() {
        background(0);
    }

    public void draw() {
        stroke(255);
        line(0,0,width,height);
    }
}