import processing.core.PApplet;

public class KeyListenerExample extends PApplet {

    public static void main(String[] args) {
        PApplet.main("KeyListenerExample");
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {

    }
    private int x, y = 0;
    private boolean[] keys = new boolean[256];
    @Override
    public void draw() {
        background(0);
        fill(255);
        text("Key pressed: " + key, 50, 50);
        move();
        noStroke();
        fill(255, 0, 23, 175);
        ellipse(x, y, 100, 100);
    }
    final int PLAYER_SPEED = 5;
    void move() {
        if(keys[87]) {
            y -= PLAYER_SPEED;
        } else if(keys[65]) {
            x -= PLAYER_SPEED;
        } else if(keys[83]) {
            y += PLAYER_SPEED;
        } else if(keys[68]) {
            x += PLAYER_SPEED;
        }
    }

    @Override
    public void keyPressed() {
        keys[keyCode] = true;
    }

    @Override
    public void keyReleased() {
        keys[keyCode] = false;
    }
}
