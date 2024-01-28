package de.tum.cit.ase.maze;

import java.util.Arrays;

public class FogOfWar {
    Boolean[][] fog;
    private static final int FOG_BORDER_WIDTH = 10;

    public FogOfWar(Maze maze) {
        fog = new Boolean[maze.getM()+FOG_BORDER_WIDTH*2][maze.getN()+FOG_BORDER_WIDTH*2];
        for (Boolean[] row : fog) {
            Arrays.fill(row, true);
        }
    }

    public boolean isFoggy(int x, int y) {
        x += FOG_BORDER_WIDTH;
        y += FOG_BORDER_WIDTH;

        if (x < 0 || x >= fog.length
                || y < 0 || y >= fog[0].length) {
            return true;
        }

        return fog[x][y];
    }

    public void reveal(int x, int y) {
        x += FOG_BORDER_WIDTH;
        y += FOG_BORDER_WIDTH;

        if (x < 0 || x >= fog.length
                || y < 0 || y >= fog[0].length) {
            return;
        }

        fog[x][y] = false;
    }

    public void restart() {
        for (int x=0; x<fog.length; x++) {
            for (int y=0; y<fog[0].length; y++) {
                fog[x][y] = true;
            }
        }
    }
}
