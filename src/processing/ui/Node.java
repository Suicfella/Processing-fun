package processing.ui;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private UIElement parent;
    private List<UIElement> children;

    public Node(UIElement parent) {
        this.parent = parent;
        this.children = new ArrayList<>();
    }
}
