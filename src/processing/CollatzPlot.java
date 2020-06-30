package processing;

import processing.core.PApplet;
import processing.core.PVector;

import java.lang.invoke.MethodHandles;

public class CollatzPlot extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
        smooth(16);
    }

    private Player player;

    @Override
    public void setup() {
        background(0);
        player = new Player(new PVector(0, 0));
        cameraPos = new PVector(0, 0);
        colorMode(HSB, 360, 100, 100);
    }

    private PVector cameraPos;

    private boolean[] keys = new boolean[256];

    private int xOffset = 0;
    private int yOffset = 0;

    @Override
    public void draw() {
        // background(0);
        // text("Player pos: " + player.position.x + " | " + player.position.y, 25, 25);
        //  text("Camera pos: " + cameraPos.x + " | " + cameraPos.y, 25, 45);
        translate(radius + xOffset, radius + yOffset);
        drawCircle();
        //   player.display();
        //  player.move();
        //     updatePosition();
    }

    private float target = 0;
    private int increment = 35;
    private int radius = 35;

    private void drawCircle() {

        strokeWeight(2);
        pushMatrix();
        translate(xOffset, yOffset);
        noFill();
        stroke(255);
        rotateY(0.5f);
        box(radius);
        popMatrix();

        target += 0.5;
        if (target > TAU + 0.5) {
            xOffset += increment;
            if (xOffset >= width) {
                xOffset = 0;
                yOffset += increment;
            }
            target = 0;
        }

    }

    @Override
    public void mousePressed() {

    }

    private void updatePosition() {
        pushMatrix();
        translate(cameraPos.x, cameraPos.y);
        player.position.x = lerp(player.position.x, cameraPos.x, 0.03f);
        player.position.y = lerp(player.position.y, cameraPos.y, 0.03f);
        popMatrix();
    }

    @Override
    public void keyPressed() {
        keys[keyCode] = true;
    }

    @Override
    public void keyReleased() {
        keys[keyCode] = false;
    }

    class Player {

        private PVector position;

        public Player(PVector position) {
            this.position = position;
        }

        public void display() {
            pushMatrix();
            stroke(255);
            strokeWeight(3);
            ellipse(position.x, position.y, 50, 50);
            popMatrix();
        }

        public void move() {
            if (keys[68]) {
                cameraPos.x += 10;
            } else if (keys[65]) {
                cameraPos.x -= 10;
            }
        }

        public PVector getPosition() {
            return position;
        }

        public void setPosition(PVector position) {
            this.position = position;
        }
    }


}
