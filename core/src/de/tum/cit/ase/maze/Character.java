package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Vector2;

public class Character {
    Vector2 position;
    int health;
    int keys;
    private Maze maze;

    public Character(Maze maze) {
        this.maze = maze;
        position = new Vector2(0, 0);
        health = Constants.MAX_HEALTH;
        keys = 0;
    }

    public void teleportToEntry() {
        for (int x=0; x<maze.getM(); x++) {
            for (int y=0; y<maze.getN(); y++) {
                Tile tile = maze.getTile(x, y);
                if (tile != null && tile.getType() == TileType.ENTRY) {
                    position.set(tile.x * Constants.TILE_SIZE, tile.y * Constants.TILE_SIZE);
                }
            }
        }
    }

    public void restart() {
        teleportToEntry();
    }
}