package processing.games.somegame;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    private List<Particle> particles = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private PImage hacker132;
    private PImage thief132;
    @Override
    public void setup() {
        background(0);
        textSize(20);
        hacker132 = loadImage("hacker132.png");
        thief132 = loadImage("thief132.png");

    }
    private float lastRemoval = 0;
    private float lastSpawn = 0;
    private int score = 0;
    private int delay = 250;
    @Override
    public void draw() {
        background(0);


        if (millis() > lastSpawn + delay && millis() > lastRemoval + delay) {
            enemies.add(new Enemy(150, (int) random(100, width - 100), (int) random(75, 150)));
            lastSpawn = millis();
        }

        particles.forEach(Particle::render);
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            enemy.render();

            Iterator<Particle> particleIterator = particles.iterator();
            while (particleIterator.hasNext()) {
                Particle p = particleIterator.next();
                if (enemy.intersects(p)) {
                    particleIterator.remove();
                    enemyIterator.remove();
                    lastRemoval = millis();
                    score++;
                    break;
                }
            }

        }

        fill(255);
        text("Score: " + score, 10, 30);
    }

    @Override
    public void mouseDragged() {
        particles.add(new Particle(15, new PVector(mouseX, mouseY), (int) random(4, 7), 255, color(255, 0, 0)));
    }

    class Particle {

        private int radius;
        private PVector position;
        private int speed;
        private int lifespan;
        private int color;

        public Particle(int radius, PVector position, int speed, int lifespan, int color) {
            this.radius = radius;
            this.position = position;
            this.speed = speed;
            this.lifespan = lifespan;
            this.color = color;
        }

        public void render() {
            update();
            pushMatrix();
            noStroke();
            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = color & 0xFF;
            int alpha = (int) map(position.y, height, 0, 255, 0);
            tint(255, alpha);
            image(thief132, position.x, position.y);
            popMatrix();
        }

        public void update() {
            move();
            lifespan--;
        }

        private void move() {
            position.y -= speed;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public PVector getPosition() {
            return position;
        }

        public void setPosition(PVector position) {
            this.position = position;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getLifespan() {
            return lifespan;
        }

        public void setLifespan(int lifespan) {
            this.lifespan = lifespan;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }


    }

    class Enemy {
        private int radius;
        private int x;
        private int y;

        public Enemy(int radius, int x, int y) {
            this.radius = radius;
            this.x = x;
            this.y = y;
        }

        public void render() {
            pushMatrix();
            noTint();
            image(hacker132, x, y);
            popMatrix();
        }


        public boolean intersects(Particle particle) {
            boolean intersects = collides(this.x, this.y, this.radius, particle.position.x, particle.position.y, particle.radius);
            if (intersects) {
                System.out.println("intersected");
            }
            return intersects;
        }

        private boolean collides(float c1x, float c1y, float c1r, float c2x, float c2y, float c2r) {

            // get distance between the circle's centers
            // use the Pythagorean Theorem to compute the distance
            float distX = c1x - c2x;
            float distY = c1y - c2y;
            float distance = sqrt((distX * distX) + (distY * distY));

            // if the distance is less than the sum of the circle's
            // radii, the circles are touching!
            if (distance <= c1r + c2r) {
                return true;
            }
            return false;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}
