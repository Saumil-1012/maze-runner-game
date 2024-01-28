package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.maze.MovableEntity;

public class Character extends MovableEntity {
    int health;
    int keys;
    private static final float MOVE_SPEED = 2f;

    public Character(Maze maze) {
        super(new Rectangle(0, 0, 0, 0), maze, 0.75f);
        boundingBox.setSize(Constants.TILE_SIZE-4, Constants.TILE_SIZE-6);
        health = Constants.MAX_HEALTH;
        keys = 0;
        opaqueTileTypes.add(TileType.EXIT);
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position.set(position);
        boundingBox.setPosition(new Vector2(position.x + 2, position.y));
    }

    public void teleportToEntry() {
        for (int x=0; x<maze.getM(); x++) {
            for (int y=0; y<maze.getN(); y++) {
                Tile tile = maze.getTile(x, y);
                if (tile != null && tile.getType() == TileType.ENTRY) {
                    setPosition(tile.x * Constants.TILE_SIZE, tile.y * Constants.TILE_SIZE);
                }
            }
        }
    }

    public void restart() {
        teleportToEntry();
    }

    public void checkForCollision() {
        int xIndex = (int) (position.x / Constants.TILE_SIZE);
        int yIndex = (int) (position.y / Constants.TILE_SIZE);

        Tile collidedWith = null;
        collidedWith = isCollidingThenTile(xIndex, yIndex);
        if (collidedWith==null) collidedWith = isCollidingThenTile(xIndex-1, yIndex);
        if (collidedWith==null) collidedWith = isCollidingThenTile(xIndex+1, yIndex);
        if (collidedWith==null) collidedWith = isCollidingThenTile(xIndex, yIndex-1);
        if (collidedWith==null) collidedWith = isCollidingThenTile(xIndex, yIndex+1);
        if (collidedWith==null) collidedWith = isCollidingThenTile(xIndex-1, yIndex-1);
        if (collidedWith==null) collidedWith = isCollidingThenTile(xIndex-1, yIndex+1);
        if (collidedWith==null) collidedWith = isCollidingThenTile(xIndex+1, yIndex-1);
        if (collidedWith==null) collidedWith = isCollidingThenTile(xIndex+1, yIndex+1);

        if (collidedWith != null) {
            switch (collidedWith.getType()) {
                case EXIT:
                    onCollisionWithExit();
                    break;
                case KEY:
                    onCollisionWithKey(collidedWith);
                    break;
            }
        }

        for (Enemy enemy : maze.getEnemies()) {
            if (isColliding(enemy)) {
                onCollisionWithEnemy(enemy);
            }
        }

        for (Trap trap : maze.getTraps()) {
            if (isColliding(trap)) {
                onCollisionWithTrap(trap);
            }
        }
    }

    private void onCollisionWithEnemy(Enemy enemy) {
        AttackingEntity attackingEntity = enemy.getAttackingEntity();
        if (attackingEntity.canAttack()) {
            attackingEntity.attack();
            decrementHealth();
        }
    }
    private void onCollisionWithTrap(Trap trap) {
        AttackingEntity attackingEntity = trap.getAttackingEntity();
        if (attackingEntity.canAttack()) {
            attackingEntity.attack();
            decrementHealth();
        }
    }
    private void onCollisionWithExit() {}
    private void onCollisionWithKey(Tile tile) {
        if (!tile.isInteracted) {
            tile.isInteracted = true;
            keys++;

            if (keys >= maze.getTotalKeys()) {
                opaqueTileTypes.remove(TileType.EXIT);
            }
        }
    }

    // If colliding with the tile then return the tile else return null
    private Tile isCollidingThenTile(int tileX, int tileY) {
        Tile tile = maze.getTile(tileX, tileY);
        if (tile == null) return null;
        if (isColliding(tile)) {
            return tile;
        }
        return null;
    }

    private void decrementHealth() {
        if (health > 0) {
            health--;
        }
    }
}
