package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;

public class Enemy extends MovableEntity {
    int origX, origY;
    private Vector2 position;
    private Direction direction;
    private float localTime = 0f;

    public Enemy(int x, int y, Maze maze) {
        super(new Rectangle(0, 0, 0, 0), maze, 0.25f);

        this.direction = getRandomDirection();
        origX = x;
        origY = y;
        setPosition(x*Constants.TILE_SIZE, y*Constants.TILE_SIZE);
        boundingBox.setSize(Constants.TILE_SIZE);
    }

    private Direction getRandomDirection() {
        // Pick a random direction
        int dir = random.nextInt(4);
        switch (dir) {
            case 0: return Direction.UP;
            case 1: return Direction.RIGHT;
            case 2: return Direction.DOWN;
            case 3: return Direction.LEFT;
        }

        return Direction.UP;
    }

    public void update(float delta) {
        localTime += delta;
        if (localTime > random.nextInt(4) + 4) {
            localTime = 0;
            direction = getRandomDirection();
        }

        Vector2 dir = direction.getVector();
        if (!canMove(dir.x, dir.y)) {
            direction = getRandomDirection();
        }

        move(direction);
    }
}

