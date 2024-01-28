package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Rectangle;

public class Collider {
    protected Rectangle boundingBox;
    Collider(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public boolean isColliding(Collider collider) {
        return boundingBox.overlaps(collider.boundingBox);
    }
}