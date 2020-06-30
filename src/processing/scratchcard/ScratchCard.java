package processing.scratchcard;

import processing.ShaderSketch;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;


public class ScratchCard extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(ScratchCard.class.getName());
    }

    private int scratchRadius = 100;

    @Override
    public void settings() {
        size(800, 600, P2D);
    }

    private ImageWrapper img;
    private int[] originalPixels;

    @Override
    public void setup() {
        background(20);
        // imageMode(CENTER);
        img = new ImageWrapper(new PVector(0, 0), loadImage("128whip.png"));
        img.getImage().loadPixels();
        originalPixels = new int[img.getImage().pixels.length];
        for (int i = 0; i < img.getImage().pixels.length; i++) {
            originalPixels[i] = img.getImage().pixels[i];
            img.getImage().pixels[i] = color(255, 0, 0);
        }
        img.getImage().updatePixels();

    }

    @Override
    public void draw() {
        background(20);
        image(img.getImage(), 0, 0);
    }

    @Override
    public void mouseDragged() {
        // plotCircle(img, mouseX, mouseY, scratchRadius);
       // plotPoint(img, mouseX, mouseY, scratchRadius);
        // loadPixels();
        // img.pixels = originalPixels;
        //updatePixels();
        boolean inside = insideImage(img);
        if(inside) {
            img.getImage().loadPixels();
            int index = mouseX + mouseY * img.getImage().width;
            for(int i = 0; i < scratchRadius; i++) {
                int firstIndex = mouseX + mouseY * img.getImage().width + i;
                int secondIndex = mouseX + mouseY + img.getImage().width - i;
                plotPoint(img.getImage().pixels, originalPixels, firstIndex);
                plotPoint(img.getImage().pixels, originalPixels, secondIndex);
            }
            img.getImage().updatePixels();
            System.out.println("Updated pixels");
        }
        println("Updated pixels");
    }

    private void plotPoint(int[] tempPixels, int[] originalPixels, int index) {
        setPixel(tempPixels, originalPixels, index);
    }

    private void setPixel(int[] tempPixels, int[] originalPixels, int index) {
        if(tempPixels.length > index) {
            tempPixels[index] = originalPixels[index];
        }
    }

    @Override
    public void mousePressed() {
        boolean inside = insideImage(img);
        if(inside) {
            img.getImage().loadPixels();
        /*    int index = mouseX + mouseY * img.getImage().width;
            for(int i = 0; i < scratchRadius; i++) {
                int firstIndex = mouseX + mouseY * img.getImage().width + i;
                int secondIndex = mouseX + mouseY + img.getImage().width - i;
                img.getImage().pixels[firstIndex] = originalPixels[firstIndex];
                img.getImage().pixels[secondIndex] = originalPixels[secondIndex];
            }´*/
         //   img.getImage().pixels[index] = originalPixels[index];
            Vector2d clickedPoint = calculatePoint(mouseX, mouseY, scratchRadius, true);
            int index = (int) (clickedPoint.getX() + clickedPoint.getY() + img.getImage().width);
            setPixel(img.getImage().pixels, originalPixels, index);
            img.getImage().updatePixels();
            System.out.println("Updated pixels");
        }
    }

    private Vector2d calculatePoint(int originX, int originY, int radius, boolean isUniform) {
        double angle = Math.random() * PI * 2;
        double r = (isUniform ? Math.sqrt(Math.random()) : Math.random()) * radius;
        double x = originX + r * Math.cos(angle);
        double y = originY + r * Math.sin(angle);
        return new Vector2d(x, y);
    }

    private boolean insideImage(ImageWrapper imageWrapper) {
        PVector position = imageWrapper.getPosition();
        int imgWidth = imageWrapper.getImage().width;
        int imgHeight = imageWrapper.getImage().height;
        return mouseX > position.x && mouseX < position.y + imgWidth && mouseY > position.y && mouseY < position.y + imgHeight;
    }

    private void plotPoint(PImage img, int mX, int mY, int radius) {
        img.loadPixels();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double dx = x - mX;
                double dy = y - mY;
                double distanceSquared = dx * dx + dy * dy;

                if (distanceSquared <= radius) {
                    pixels[x + y * width] = originalPixels[x + y * width];
                }
            }
        }
        img.updatePixels();
    }

    private void plotCircle(PImage img, final int centerX, final int centerY, final int radius) {
        int d = (5 - radius * 4) / 4;
        int x = 0;
        int y = radius;

        img.loadPixels();

        do {
            img.pixels[(centerX + x) + (centerY + y) * img.width] = originalPixels[(centerX + x) + (centerY + y) * img.width];
            img.pixels[(centerX + x) + (centerY - y) * img.width] = originalPixels[(centerX + x) + (centerY - y) * img.width];
            img.pixels[(centerX - x) + (centerY + y) * img.width] = originalPixels[(centerX - x) + (centerY + y) * img.width];
            img.pixels[(centerX - x) + (centerY - y) * img.width] = originalPixels[(centerX - x) + (centerY - y) * img.width];
            img.pixels[(centerX + x) + (centerY + y) * img.width] = originalPixels[(centerX + x) + (centerY + y) * img.width];
            img.pixels[(centerX + x) + (centerY - y) * img.width] = originalPixels[(centerX + x) + (centerY - y) * img.width];
            img.pixels[(centerX - x) + (centerY + y) * img.width] = originalPixels[(centerX - x) + (centerY + y) * img.width];
            img.pixels[(centerX - x) + (centerY - y) * img.width] = originalPixels[(centerX - x) + (centerY - y) * img.width];
            System.out.println("Set pixels");
            if (d < 0) {
                d += 2 * x + 1;
            } else {
                d += 2 * (x - y) + 1;
                y--;
            }
            x++;
        } while (x <= y);
        img.updatePixels();
    }

}
