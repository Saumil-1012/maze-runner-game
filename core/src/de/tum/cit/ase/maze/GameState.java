package de.tum.cit.ase.maze;

public class GameState {
    private final Maze maze;
    private final Character character;

    public GameState(Maze maze, Character character) {
        this.maze = maze;
        this.character = character;
    }

    public Maze getMaze() { return maze; }

    public Character getCharacter() { return character; }

    public void restart() {
        maze.restart();
        character.restart();
    }
}
