package processing.scratchcard;

import processing.core.PImage;
import processing.core.PVector;

public class ImageWrapper {
    private PVector position;
    private PImage image;

    public ImageWrapper(PVector position, PImage image) {
        this.position = position;
        this.image = image;
    }

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public PImage getImage() {
        return image;
    }

    public void setImage(PImage image) {
        this.image = image;
    }
}

