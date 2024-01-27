package de.tum.cit.ase.maze;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class Maze {
    private ArrayList<ArrayList<Tile>> tiles;
    private int m, n; // Dimensions of maze: m x n

    public Maze() {
        tiles = new ArrayList<>();
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
    }

    public int getM() { return m; }

    public int getN() { return n; }
    public Tile getTile(int x, int y) {
        return tiles.get(x).get(y);
    }

    public void restart() {
    }
}