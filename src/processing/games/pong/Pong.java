package processing.games.pong;

import processing.core.PApplet;
import processing.core.PVector;

import java.lang.invoke.MethodHandles;

public class Pong extends PApplet {
    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    private boolean[] keys;
    private Ball ball;
    private Paddle playerPaddle;

    @Override
    public void setup() {
        background(0);
        keys = new boolean[512];
        ball = new Ball(50);
        playerPaddle = new Paddle(50, height / 2, 15,120);
        rectMode(CENTER);
    }

    @Override
    public void draw() {
        background(0);
        rect(playerPaddle.getX(), playerPaddle.getY(), playerPaddle.getWidth(), playerPaddle.getHeight());
        handleInput();
        ball.render();
        ball.handleBallIntersection(playerPaddle);
    }

    private void handleInput() {
        if (keys[65]) {
            playerPaddle.decrementY(10);
            adjustPosition();
        } else if (keys[68]) {
            playerPaddle.incrementY(10);
            adjustPosition();
        }
    }

    private void adjustPosition() {
        int constrainedY = constrain(playerPaddle.getY(), 60, height - 60);
        playerPaddle.setY(constrainedY);
    }

    @Override
    public void keyPressed() {
        keys[keyCode] = true;
    }

    @Override
    public void keyReleased() {
        keys[keyCode] = false;
    }

    class Ball {
        private int radius;
        private PVector position;
        private PVector velocity;
        private int speed;

        public Ball(int radius) {
            this.radius = radius;
            this.position = new PVector(width / 2f, height / 2f);
            this.velocity = new PVector(-5, 0.1f);
            this.speed = 5;
        }

        public void render() {
            handleWallIntersection();
            move();
            pushMatrix();
            stroke(255);
            ellipse(position.x, position.y, radius, radius);
            popMatrix();
        }

        private void move() {
            this.position.add(velocity);
        }

        public void handleWallIntersection() {
            if(this.position.x - (radius / 2f) >= width - (radius / 2f)) {
                this.velocity.set(-5, random(7));
            } else if(this.position.x <= radius / 2f) {
                this.velocity.set(5, random(7));
            } else if(this.position.y - (radius / 2f) >= height - (radius / 2f)) {
                this.velocity.set(random(7), -5);
            } else if(this.position.y <= radius / 2f) {
                this.velocity.set(random(7), 5);
            }
        }

        public void handleBallIntersection(Paddle paddle) {
            boolean intersected = collides(this.position.x, this.position.y, this.radius, paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
            if(intersected) {
                System.out.println("yes");
                float offset = (this.position.x + this.radius - paddle.getX()) /
                (paddle.getWidth() + this.radius);
                float phi = 0.25f * PI * (2 * offset - 1);

                velocity.x = this.speed * sin(phi);
                velocity.y *= -1;
            }
        }

        // CIRCLE/RECTANGLE
        boolean collides(float cx, float cy, float radius, float rx, float ry, float rw, float rh) {

            // temporary variables to set edges for testing
            float testX = cx;
            float testY = cy;

            // which edge is closest?
            if (cx < rx)         testX = rx;      // test left edge
            else if (cx > rx+rw) testX = rx+rw;   // right edge
            if (cy < ry)         testY = ry;      // top edge
            else if (cy > ry+rh) testY = ry+rh;   // bottom edge

            // get distance from closest edges
            float distX = cx-testX;
            float distY = cy-testY;
            float distance = sqrt( (distX*distX) + (distY*distY) );

            // if the distance is less than the radius, collision!
            if (distance <= radius) {
                return true;
            }
            return false;
        }

        public PVector getPosition() {
            return position;
        }

        public void setPosition(PVector position) {
            this.position = position;
        }
    }

    class Paddle {
        private int x, y, width, height;

        public Paddle(int x, int y, int width, int height) {
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

        public void incrementX(int amount) {
            this.x += amount;
        }

        public void decrementX(int amount) {
            this.x -= amount;
        }

        public void incrementY(int amount) {
            this.y += amount;
        }

        public void decrementY(int amount) {
            this.y -= amount;
        }
    }
}
