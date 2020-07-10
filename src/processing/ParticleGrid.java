package processing;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class ParticleGrid extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
        smooth(16);
    }

    private float[][] grid;
    private int cols, rows;
    private int scale = 150;
    private PImage tex;

    @Override
    public void setup() {
        background(0);
        cols = (width * 3) / scale;
        rows = (height * 3) / scale;
        grid = new float[cols][rows];
        tex = loadImage("noisetex.png");
        System.out.println(lerp(0, 10, 0.2f));
    }

    private List<Particle> particles = new ArrayList<>();

    @Override
    public void draw() {
        background(0);
        handleMove();
        drawGrid();
        if (mousePressed) {
            for(int i = 0; i < 25; i++) {
                particles.add(new Particle(new PVector((width / 2f) + random(-200, 200), random(-350, -50), random(-250, -50))));
            }
        }
        particles.forEach(Particle::emit);
    }

    private int z = 0;

    @Override
    public void mouseWheel(MouseEvent event) {
        if (event.getCount() == 1) {
            z += 200;
        } else {
            z -= 200;
        }
    }

    private float xRotation = 0;
    private float yRotation = 0;

    private float rotationSpeed = 0.001f;

    private boolean[] keys = new boolean[256];

    @Override
    public void keyPressed() {
        keys[keyCode] = true;
    }

    @Override
    public void keyReleased() {
        keys[keyCode] = false;
    }

    private int xOffset = 0;
    private int yOffset = 0;
    private int boxZ = 0;

    private void handleMove() {
        if (keys[65]) {
            xOffset += 25;
        }
        if (keys[68]) {
            xOffset -= 25;
        }
        if (keys[87]) {
            yOffset += 25;
        }
        if (keys[83]) {
            yOffset -= 25;
        }


    }

    private void renderShader(String shaderName, boolean filter) {
        String vignettePass = shaderName + ".glsl";
        uniform(vignettePass).set("time", radians(frameCount));
        uniform(vignettePass).set("tex", tex);
        if (filter) {
            hotFilter(vignettePass);
        } else {
            hotShader(vignettePass);
        }
    }

    private void drawGrid() {
        text("z: " + boxZ, 50, 50);
        pushMatrix();
        stroke(255);
        rotateX(xRotation);
        rotateY(yRotation);
        renderShader("cloudshader", true);
        translate(xOffset, yOffset, z);
        rotateX(PI / 3);
        translate(-width / 2f, -height / 2f);

        for (int y = 0; y < rows - 1; y++) {
            beginShape(QUAD_STRIP);
            for (int x = 0; x < cols; x++) {
                float grey = map(x, 0, cols, 15, 100);
                fill(grey);
                vertex(x * scale, y * scale, grid[x][y]);
                vertex(x * scale, (y + 1) * scale, grid[x][y + 1]);

            }
            endShape();
        }

        resetShader();

        popMatrix();
    }

    class Particle {

        PVector position;

        public Particle(PVector position) {
            this.position = position;
        }
        private int lifespan = 255;
        public void emit() {
            pushMatrix();
            noStroke();
            fill(127, 0, 127, lifespan);
            translate(position.x, position.y, position.z);
            ellipse(0, 0, 8, 8);
            popMatrix();

            position.y += 4;
            lifespan -= 2;
        }

        public PVector getPosition() {
            return position;
        }

        public void setPosition(PVector position) {
            this.position = position;
        }
    }

}
