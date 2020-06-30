import processing.core.PApplet;

public class JumpingPlayer extends PApplet {

    public static void main(String[] args) {
        PApplet.main("JumpingPlayer");
    }

    @Override
    public void settings() {
        size(800, 600);

    }


    @Override
    public void setup() {
        background(0);
    }

    int dest = 0;
    
    @Override
    public void draw() {
        background(0);
        stroke(255);
        strokeWeight(5);
        for(int y = -20; y <= dest; y += height / 20) {
            line(0, y, width, y);

        }

        for(int y = height; y >= dest; y -= height / 20) {

            line(0, y, width, y); // improve this
        }

        dest += height / 20;

        dest %= width / 2;


    }
}
