package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Character extends MovableEntity {
    int health;
    int keys;
    private final CharacterListener listener;
    private float lastSpeedBoostPickedTime = 99;
    private static final float SPEED_BOOST_DURATION = 10f; // 10 Seconds
    private static final float SPEED_BOOST_FACTOR = 1.5f;

    public Character(Maze maze, CharacterListener listener) {
        super(new Rectangle(0, 0, 0, 0), maze, 0.75f);
        boundingBox.setSize(Constants.TILE_SIZE-4, Constants.TILE_SIZE-6);
        health = Constants.MAX_HEALTH;
        keys = 0;
        opaqueTileTypes.add(TileType.EXIT);
        this.listener = listener;
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
        health = Constants.MAX_HEALTH;
        keys = 0;
        lastSpeedBoostPickedTime = 99f;
        setMoveSpeed(0.75f);
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
                case SPEED:
                    onCollisionWithSpeed(collidedWith);
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
            listener.onCollisionWithEnemy(enemy);
            if (health <= 0) {
                listener.onDead();
            }
        }
    }
    private void onCollisionWithTrap(Trap trap) {
        AttackingEntity attackingEntity = trap.getAttackingEntity();
        if (attackingEntity.canAttack()) {
            attackingEntity.attack();
            decrementHealth();
            listener.onCollisionWithTrap(trap);
            if (health <= 0) {
                listener.onDead();
            }
        }
    }
    private void onCollisionWithExit() {
        listener.onCollisionWithExit();
    }
    private void onCollisionWithKey(Tile tile) {
        if (!tile.isInteracted) {
            tile.isInteracted = true;
            keys++;

            if (hasEnoughKeys()) {
                opaqueTileTypes.remove(TileType.EXIT);
            }

            listener.onCollisionWithKey(tile);
        }
    }

    private void onCollisionWithSpeed(Tile tile) {
        if (tile.isInteracted) return;
        tile.isInteracted = true;
        if (!isSpeedBoostActive()) {
            setMoveSpeed(getMoveSpeed() * SPEED_BOOST_FACTOR);
        }
        lastSpeedBoostPickedTime = 0f;
        listener.onCollisionWithSpeed(tile);
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

    public boolean hasEnoughKeys() {
        return keys >= maze.getTotalKeys();
    }

    public void updateFogOfWar(FogOfWar fogOfWar) {
        int radius = 6;
        float distanceFoggy = radius*Constants.TILE_SIZE;
        float distanceHidden = (radius+2)*Constants.TILE_SIZE;

        float xCenter = position.x + (float) Constants.TILE_SIZE/2;
        float yCenter = position.y + (float) Constants.TILE_SIZE/2;

        int xIndex = (int) (position.x / Constants.TILE_SIZE);
        int yIndex = (int) (position.y / Constants.TILE_SIZE);
        int OX = xIndex-radius-1, EX = xIndex+radius+1;
        int OY = yIndex-radius-1, EY = yIndex+radius+1;

        for (int x=OX; x<=EX; x++) {
            for (int y=OY; y<=EY; y++) {
                float tileXCenter = (x+0.5f) * Constants.TILE_SIZE;
                float tileYCenter = (y+0.5f) * Constants.TILE_SIZE;

                float distanceFromCharacterSquared = (float) (Math.pow(tileXCenter-xCenter, 2)+Math.pow(tileYCenter-yCenter, 2));
                if (distanceFromCharacterSquared <= distanceFoggy*distanceFoggy) {
                    fogOfWar.reveal(x, y);
                }
                if (distanceFromCharacterSquared <= distanceHidden*distanceHidden) {
                    Tile tile = maze.getTile(x, y);
                    if (tile != null) {
                        tile.isRevealed = true;
                    }
                }
            }
        }
    }

    public void update(float delta) {
        if (lastSpeedBoostPickedTime <= SPEED_BOOST_DURATION) {
            lastSpeedBoostPickedTime += delta;
            if (lastSpeedBoostPickedTime > SPEED_BOOST_DURATION) setMoveSpeed(getMoveSpeed() / SPEED_BOOST_FACTOR);
        }
    }

    public float getSpeedBoostLeftTime() {
        if (!isSpeedBoostActive()) return 0;
        return SPEED_BOOST_DURATION - lastSpeedBoostPickedTime;
    }

    public boolean isSpeedBoostActive() {
        return lastSpeedBoostPickedTime <= SPEED_BOOST_DURATION;
    }
}
