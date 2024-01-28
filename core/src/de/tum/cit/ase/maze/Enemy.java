package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;

public class Enemy extends MovableEntity {
    int origX, origY;
    private Vector2 position;
    private Direction direction;
    private float localTime = 0f;
    private AttackingEntity attackingEntity;

    public Enemy(int x, int y, Maze maze) {
        super(new Rectangle(0, 0, 0, 0), maze, 0.25f);

        this.direction = getRandomDirection();
        origX = x;
        origY = y;
        setPosition(x*Constants.TILE_SIZE, y*Constants.TILE_SIZE);
        boundingBox.setSize(Constants.TILE_SIZE);
        this.attackingEntity = new AttackingEntity();
        opaqueTileTypes.add(TileType.ENTRY);
        opaqueTileTypes.add(TileType.EXIT);
    }

    private Direction getRandomDirection() {
        int dir = random.nextInt(4);
        return switch (dir) {
            case 0 -> Direction.UP;
            case 1 -> Direction.RIGHT;
            case 2 -> Direction.DOWN;
            default -> Direction.LEFT;
        };
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
        attackingEntity.update(delta);
    }

    public AttackingEntity getAttackingEntity() { return attackingEntity; }
}

