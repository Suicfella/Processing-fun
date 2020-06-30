package processing.algorithms;

import processing.core.PApplet;
import processing.core.PVector;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    @Override
    public void settings() {
        size(1400, 900);
       // smooth(16);

    }

    private Tree tree;
    private Set<Integer> values = new HashSet<>();
    @Override
    public void setup() {
        background(0);
        tree = new Tree();
        tree.addValue(245);
        for (int i = 0; i < 15; i++) {
            tree.addValue((int) random(5, 499));
        }
        tree.traverse();

        Node searchedNode = tree.search(33);
        if(searchedNode == null) {
            System.out.println("Not found");
        } else {
            System.out.println("Found " + searchedNode.value);
        }

    }

    @Override
    public void draw() {
       // background(0);
    }

    class Node {

        public int value;
        public Node left;
        public Node right;

        public int x;
        public int y;

        public Node(int value, int x, int y) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.x = x;
            this.y = y;
        }

        public Node(int value) {
            this(value, 0, 0);
        }

        public void addNode(Node n) {
            if (n.value < this.value) {
                if (this.left == null) {
                    this.left = n;
                    this.left.x = this.x - 80;
                    this.left.y = this.y + 40;
                } else {
                    this.left.addNode(n);
                }
            } else if (n.value > this.value) {
                if (this.right == null) {
                    this.right = n;
                    this.right.x = this.x + 80;
                    this.right.y = this.y + 40;
                } else {
                    this.right.addNode(n);
                }
            }
        }

        public Node search(int value) {
            if (this.value == value) {
                // System.out.println("Found " + value);
                return this;
            } else if (value < this.value && this.left != null) {
                return this.left.search(value);
            } else if (value > this.value && this.right != null) {
                return this.right.search(value);
            }
            return null;
        }

        public void visit(Node parent) {


            if (this.left != null) {
                this.left.visit(this);
            }
            System.out.println(this.value);
            fill(255);
            noStroke();
            textAlign(CENTER);

            text(this.value, this.x, this.y);
            stroke(255);
            noFill();
            ellipse(this.x, this.y, 35, 35);
            PVector start = new PVector(parent.x, parent.y);
            PVector end = new PVector(this.x, this.y);
            PVector connecting = end.copy().sub(start);
            PVector direction = connecting.normalize();

            PVector arrowStart = start.copy().add(direction.copy().mult(70));
            PVector arrowEnd = end.copy().sub(direction.copy().mult(70));
            line(arrowStart.x, arrowStart.y, arrowEnd.x, arrowEnd.y);
            if (this.right != null) {
                this.right.visit(this);
            }
        }


    }

    class Tree {
        public Node root;

        public Tree() {
            root = null;
        }

        public void addValue(int value) {
            Node n = new Node(value);
            if (root == null) {
                root = n;
                this.root.x = width / 2;
                this.root.y = 100;
            } else {
                root.addNode(n);
            }
        }

        public void traverse() {
            root.visit(this.root);
        }

        public Node search(int value) {
            Node found = this.root.search(value);
            return found;
        }
    }


}
