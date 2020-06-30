package processing.game;

import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Game extends PApplet {


    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        //size(int(600/scale)scale, int(400/scale)scale); and it should line up
        size(600, 600, P2D);
        smooth(16);
    }

    private int rows;
    private int columns;
    private Cell[][] cells;
    private int spacing;
    List<PImage> sprites = new ArrayList<>();
    private void loadSprites() {
        Path path = Paths.get("data", "sprites");
        try {

            Files.walk(path).forEach(file -> {
                PImage sprite = loadImage("data/sprites/" + "bluetexture.png");
                sprite.resize(32, 32);
                sprites.add(sprite);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() {
        background(0);
        loadSprites();
        spacing = 60;
        rows = 10;
        columns = 10;
        cells = new Cell[rows * columns][rows * columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                float x = row * spacing;
                float y = col * spacing;
                cells[row][col] = new Cell(0, x, y);
            }
        }
    }

    @Override
    public void draw() {
        background(0);
        stroke(255);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells[row][col].render();
            }
        }

    }

    @Override
    public void mousePressed() {
        cells[mouseX / spacing][mouseY / spacing].setSpriteId(1);
    }

    public class Cell {
        private int spriteId;
        private float x, y;

        public Cell(int spriteId, float x, float y) {
            this.spriteId = spriteId;
            this.x = x;
            this.y = y;
        }

        public void render() {
            stroke(255);
            fill(0);
            rect(x, y, spacing, spacing);
            if(spriteId > 0) {
                System.out.println("Rendering");
                image(sprites.get(spriteId), x + (spacing / 4f), y);
            }
        }

        public int getSpriteId() {
            return spriteId;
        }

        public void setSpriteId(int spriteId) {
            this.spriteId = spriteId;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}