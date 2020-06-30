package processing.ageofwar;

import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class Main extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    public static int width = 800, height = 600;


    @Override
    public void settings() {
        size(width, height, P3D);
        smooth(16);
    }

    private Button button;
    private Projectile projectile;
    private List<Particle> particles;

    @Override
    public void setup() {
        background(0);
        button = new Button(5, 5, 100, 30, Color.CYAN.getRGB());
        // projectile = new Projectile(new PVector(50, 50), new PVector(500, 500), new PVector(5f, 3));
        particles = new ArrayList<>();
        int radius = 200;


    }


    @Override
    public void draw() {
        background(0);
        button.render();
        particles.forEach(Particle::render);
        //projectile.render();
    }

    @Override
    public void mouseDragged() {
        button.onClick();
        for(int i = 0; i < 59; i++) {
            int col = i * 50000;
            particles.add(new Particle((int) random(250, 500), (int) random(50, 500), (int) random(1, 5), col));
        }
    }

    class Particle {

        private int x, y, radius, col, speed;

        public Particle(int x, int y, int radius, int col) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.col = (int) random(360);
            this.speed = (int) random(5, 55);
        }

        public void render() {
            update();
            pushMatrix();
            noStroke();
            colorMode(HSB, 360, 100, 100);
            fill(col, 100, 100);
            ellipse(x, y, radius, radius);
            popMatrix();
        }

        private void update() {
            this.y += speed;
        }
    }

    class Projectile {

        private PVector start, end, velocity;
        private float x, y;

        public Projectile(PVector start, PVector end, PVector velocity) {
            this.start = start;
            this.end = end;
            this.velocity = velocity;
            this.x = start.x;
            this.y = start.y;
        }

        public void render() {
            if (end.x > x) {
                x += velocity.x;
            }

            if (end.y > y) {
                y += velocity.y;
            }

            pushMatrix();
            noStroke();
            fill(255);
            ellipse(x, y, 50, 50);
            popMatrix();
        }
    }

    interface UIElement {

        boolean intersects();

    }

    class Button implements UIElement {

        private int x, y, w, h, color;
        private PImage image, hoveredImage;

        public Button(int x, int y, int w, int h, int color) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.color = color;
        }

        public Button(int x, int y, PImage image, PImage hoveredImage) {
            this.x = x;
            this.y = y;
            this.image = image;
            this.hoveredImage = hoveredImage;
        }

        @Override
        public boolean intersects() {
            boolean intersects;
            if (image != null) {
                intersects = mouseX >= x && mouseX <= x + image.width && mouseY >= y && mouseY <= y + image.height;
            } else {
                intersects = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
            }

            return intersects;
        }

        public void onClick() {
            if (!intersects()) {
                return;
            }

            System.out.println("Clicked on button");
        }

        public void render() {
            pushMatrix();
            if (image != null) {
                image(intersects() ? hoveredImage : image, x, y);
            } else {
                noStroke();
                fill(color(color), intersects() ? 255 : 50);
                rect(x, y, w, h);
            }
            popMatrix();
        }
    }
}
