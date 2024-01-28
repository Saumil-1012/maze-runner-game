package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public Vector2 getVector() {
        float dx = 0;
        float dy = 0;

        switch (this) {
            case UP -> dy = 1;
            case DOWN -> dy = -1;
            case LEFT -> dx = -1;
            case RIGHT -> dx = 1;
        }

        return new Vector2(dx, dy);
    }
}
