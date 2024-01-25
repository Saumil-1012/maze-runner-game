package de.tum.cit.ase.maze;

public class Maze {
    private MazeObject[][] cell;

    public Maze(){
        cell= new MazeObject[20][20];
    }

    public MazeObject getCell(int x,int y) {
        return cell[x][y];
    }

    public void setCell(int x, int y, MazeObject value) {
        cell[x][y] = value;
    }

    public int getWidth(){
        return cell.length;
    }

    public int getHeight(){
        return cell[0].length;
    }
}
