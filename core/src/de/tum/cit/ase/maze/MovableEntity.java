package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MovableEntity extends Collider {
    protected Vector2 position;
    protected Maze maze;
    private float moveSpeed;

    public MovableEntity(Rectangle rectangle, Maze maze, float moveSpeed) {
        super(rectangle);
        this.position = new Vector2(0, 0);
        this.maze = maze;
        this.moveSpeed = moveSpeed;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
        boundingBox.setPosition(position);
    }
    public void setPosition(float x, float y) { setPosition(new Vector2(x, y)); }
    public Vector2 getPosition() { return position; }

    public void move(Direction direction) {
        Vector2 diff = direction.getVector();
        if (canMove(diff.x, diff.y)) {
            setPosition(new Vector2(position.x + diff.x*moveSpeed, position.y + diff.y*moveSpeed));
        }
    }

    protected boolean canMove(float dx, float dy) {
        // Try moving to the target spot
        float newX = position.x + dx;
        float newY = position.y + dy;

        // Check if not out of bounds
        if (newX < 0 || newX >= maze.getM()*Constants.TILE_SIZE
                || newY < 0 || newY >= maze.getN()*Constants.TILE_SIZE) {
            return false;
        }

        setPosition(new Vector2(newX, newY));

        boolean result = true;
        // Check if colliding with a WALL
        int newXIndex = (int) (newX / Constants.TILE_SIZE);
        int newYIndex = (int) (newY / Constants.TILE_SIZE);
        result = !isCollidingWithTile(newXIndex, newYIndex)
                && !isCollidingWithTile(newXIndex - 1, newYIndex)
                && !isCollidingWithTile(newXIndex + 1, newYIndex)
                && !isCollidingWithTile(newXIndex, newYIndex - 1)
                && !isCollidingWithTile(newXIndex, newYIndex + 1)
                && !isCollidingWithTile(newXIndex - 1, newYIndex - 1)
                && !isCollidingWithTile(newXIndex - 1, newYIndex + 1)
                && !isCollidingWithTile(newXIndex + 1, newYIndex - 1)
                && !isCollidingWithTile(newXIndex + 1, newYIndex + 1);

        // Undo the move because it only needs to check if the position is valid
        newX = position.x - dx;
        newY = position.y - dy;
        setPosition(new Vector2(newX, newY));

        return result;
    }

    protected boolean isCollidingWithTile(int tileX, int tileY) {
        Tile tile = maze.getTile(tileX, tileY);
        if (tile == null || tile.getType() != TileType.WALL) return false;
        return isColliding(tile);
    }
}

