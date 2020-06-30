package processing.particlesandcubes;

import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PVector;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sketch extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    private List<Particle> particles = new ArrayList<>();

    @Override
    public void settings() {
        size(800, 600, P3D);
        smooth(16);
    }

    private int boxRadius;
    private int PARTICLE_COUNT;
    private int MAX_X;
    private int MIN_X;
    private int MAX_Y;
    private int MIN_Y;
    private int MAX_Z;
    private int MIN_Z;

    @Override
    public void setup() {
        background(0);
        boxRadius = 125;
        PARTICLE_COUNT = 100000;
        MAX_X = (width / 2) + boxRadius / 2;
        MIN_X = (width / 2) - boxRadius / 2;
        MAX_Y = (height / 2) + boxRadius / 2;
        MIN_Y = (height / 2) - boxRadius / 2;
        MAX_Z = 100;
        MIN_Z = -100;
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            float x = random(0, width);
            float y = random(0, height);
            float z = random(-75, 75);
            if (MIN_X <= x && x <= MAX_X && MIN_Y <= y && y <= MAX_Y && MIN_Z <= z && z <= MAX_Z) {  // check whether point is inside the cube or not
                System.out.println("Added particle");
                PVector position = new PVector(x, y, z);
                PVector velocity = new PVector(random(-3, 3), random(-1.5f, 1.5f));
                int radius = (int) random(25, 50);
                int color = (int) random(0xffff, 0xffffff);
                Particle particle = new Particle(position, velocity, radius, color);
                particles.add(particle);
            }
        }
    }

    @Override
    public void draw() {
        background(0);
        if(mousePressed) {
            particles.clear();
            boxRadius = (int) map(mouseX, 0, width, 0, 300);
            PARTICLE_COUNT = 10000;
            MAX_X = (width / 2) + boxRadius / 2;
            MIN_X = (width / 2) - boxRadius / 2;
            MAX_Y = (height / 2) + boxRadius / 2;
            MIN_Y = (height / 2) - boxRadius / 2;
            MAX_Z = 100;
            MIN_Z = -100;
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                float x = random(0, width);
                float y = random(0, height);
                float z = random(-75, 75);
                if (MIN_X <= x && x <= MAX_X && MIN_Y <= y && y <= MAX_Y && MIN_Z <= z && z <= MAX_Z) {  // check whether point is inside the cube or not
                    System.out.println("Added particle");
                    PVector position = new PVector(x, y, z);
                    PVector velocity = new PVector(random(-3, 3), random(-1.5f, 1.5f));
                    int radius = (int) random(25, 50);
                    int color = (int) random(0xffff, 0xffffff);
                    Particle particle = new Particle(position, velocity, radius, color);
                    particles.add(particle);
                }
            }
        }
        pushMatrix();
        translate(width / 2f, height / 2f);
        noFill();
        stroke(255, 150);
        box(boxRadius);
        popMatrix();
        particles.forEach(Particle::display);
        //   System.out.println("Drawing");
    }

    public class Particle {
        private PVector position;
        private PVector velocity;
        private int radius;
        private int color;

        public Particle(PVector position, PVector velocity, int radius, int color) {
            this.position = position;
            this.velocity = velocity;
            this.radius = radius;
            this.color = color;
        }

        public void display() {
            stroke(255, 0, 0);
            //strokeWeight(5);
            System.out.println("Drawing at:  " + position.x + " | " + position.y + " | " + position.z);
            point(position.x, position.y, position.z);
        }

        public PVector getPosition() {
            return position;
        }

        public void setPosition(PVector position) {
            this.position = position;
        }

        public PVector getVelocity() {
            return velocity;
        }

        public void setVelocity(PVector velocity) {
            this.velocity = velocity;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

}
