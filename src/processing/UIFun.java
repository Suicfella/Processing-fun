package processing;

import javafx.scene.input.KeyCode;
import processing.core.PApplet;
import processing.core.PVector;
import processing.ui.Node;
import processing.ui.UIElement;

import java.util.ArrayList;
import java.util.List;

public class UIFun extends ShaderSketch {

    public static void main(String[] args) {
        PApplet.main(UIFun.class.getName());
    }

    @Override
    public void settings() {
        size(800, 600, P2D);
        smooth(16);
    }

    private List<UIElement> uiElements;


    @Override
    public void setup() {
        background(0);
        uiElements = new ArrayList<>();
        colorMode(HSB, 1, 1, 1, 1);
        generateUI();

    }

    ColorPicker colorPicker;

    private void generateUI() {
        Button button = new Button(135, 225, 100, 100);
        Button button1 = new Button(400, 250, 100, 100);
        colorPicker = new ColorPicker(150, 150, 100);
       // uiElements.add(button);
        uiElements.add(button1);
        uiElements.add(colorPicker);
    }

    int startX = -1;
    int startY = -1;
    int endX = -1;
    int endY = -1;

    @Override
    public void draw() {
       // background(0);
        renderUI();
        stroke(0, 0, 100);
        strokeWeight(2);
        if (startX != -1) {
            pushMatrix();
            //System.out.println("Rendering");

          //  line(startX, startY, endX, endY);

            popMatrix();
        }
    }

    @Override
    public void mousePressed() {
        startX = mouseX;
        startY = mouseY;
    }

    @Override
    public void keyPressed() {
        System.out.println(keyCode);
        if(keyCode == 99) {
            println("Pressed f3");
        }
    }

    @Override
    public void mouseDragged() {
        endX = mouseX;
        endY = mouseY;
    }

    @Override
    public void mouseReleased() {

    }

    public void renderUI() {
        uiElements.forEach(UIElement::render);
    }


    public class Button implements UIElement {
        private int x;
        private int y;
        private int width;
        private int height;

        public Button(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
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

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public void render() {
            pushMatrix();
            translate(0, 0);
            noStroke();
            fill(colorPicker.hue, 100, intersects() ? 255 : 25);
            rect(x, y, width, height);
            popMatrix();
        }

        @Override
        public void handleConnection() {
            uiElements.forEach(uiElement -> {
                //TODO
            });
        }

        @Override
        public boolean intersects() {

            return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
        }
    }

    public class ColorPicker implements UIElement {
        private int x;
        private int y;
        private int radius;
        Node node;

        public ColorPicker(int x, int y, int radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.node = new Node(this);
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

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public float hue = 0f;

        @Override
        public void render() {
            float result = colorPicker();

            if (result != -1) {
                hue = result;
            }
        }

        @Override
        public void handleConnection() {

        }

        float colorPicker() {

            noStroke();
            float radius = this.radius;
            int detail = 1000;
            beginShape(TRIANGLE_STRIP);
            pushMatrix();
            translate(x, y);
            for (int i = 0; i < detail; i++) {
                float theta = map(i, 0, detail - 1, 0, TAU);
                float hue = getHueAtAngle(theta);
                fill(hue, 1, 1);
                vertex(0, 0);
                vertex(radius * cos(theta), radius * sin(theta));
            }
            endShape();
            popMatrix();
            if (!intersects()) {
                return -1;
            }
            float mouseAngle = atan2(mouseY - x, mouseX - y);
            return getHueAtAngle(mouseAngle);
        }

        float getHueAtAngle(float angle) {
            while (angle < 0) {
                angle += TAU;
            }
            return map(angle % TAU, 0, TAU, 0, 1);
        }

        @Override
        public boolean intersects() {

            return (dist(this.x, this.y, mouseX, mouseY) <= this.radius);
        }
    }

}
