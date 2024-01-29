package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class Maze {
    private final ArrayList<ArrayList<Tile>> tiles;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Trap> traps;
    private int m, n; // Dimensions of maze: m x n
    private int totalKeys = 0;

    public Maze() {
        tiles = new ArrayList<>();
        enemies = new ArrayList<>();
        traps = new ArrayList<>();
        m = n = 0;
        adjustSize();
    }

    private void adjustSize() {
        while (tiles.size() < m) {
            tiles.add(new ArrayList<>());
        }
        for (ArrayList row : tiles) {
            while (row.size() < n) {
                row.add(null);
            }
        }
    }

    public void setTile(int x, int y, Tile tile) {
        if (x+1 > m) m = x+1;
        if (y+1 > n) n = y+1;
        adjustSize();
        tiles.get(x).set(y, tile);
        if (tile.getType() == TileType.KEY) {
            totalKeys++;
        }
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addTrap(Trap trap) {
        traps.add(trap);
    }

    public int getM() { return m; }

    public int getN() { return n; }
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= m || y < 0 || y >= n) return null;
        return tiles.get(x).get(y);
    }

    public void restart() {
        for (Enemy enemy : enemies) {
            enemy.goToOriginalPosition();
        }

        for (int x=0; x<m; x++) {
            for (int y=0; y<n; y++) {
                Tile tile = getTile(x, y);
                if (tile != null) {
                    tile.isInteracted = false;
                    tile.isRevealed = false;
                }
            }
        }
    }

    public ArrayList<Enemy> getEnemies() { return enemies; }
    public ArrayList<Trap> getTraps() { return traps; }

    public int getTotalKeys() { return totalKeys; }
}
