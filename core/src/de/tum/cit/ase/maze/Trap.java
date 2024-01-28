package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Trap extends Collider {
    int x, y;
    AttackingEntity attackingEntity;

    public Trap(int x, int y) {
        super(new Rectangle(0, 0, 0, 0));
        boundingBox.setPosition(x*Constants.TILE_SIZE, y*Constants.TILE_SIZE);
        boundingBox.setSize(Constants.TILE_SIZE);

        this.x = x;
        this.y = y;
        this.attackingEntity = new AttackingEntity();
    }

    public void update(float delta) {
        attackingEntity.update(delta);
    }

    public float getX() { return x*Constants.TILE_SIZE; }
    public float getY() { return y*Constants.TILE_SIZE; }

    public AttackingEntity getAttackingEntity() { return attackingEntity; }
}
