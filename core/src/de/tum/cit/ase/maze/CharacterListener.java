package de.tum.cit.ase.maze;

public interface CharacterListener {
    void onCollisionWithEnemy(Enemy enemy);
    void onCollisionWithTrap(Trap trap);
    void onCollisionWithExit();
    void onCollisionWithKey(Tile tile);
    void onCollisionWithSpeed(Tile tile);
    void onDead();
}
