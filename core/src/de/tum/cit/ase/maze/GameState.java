package de.tum.cit.ase.maze;

public class GameState {
    private final Maze maze;
    private final Character character;
    private final FogOfWar fogOfWar;

    public GameState(Maze maze, Character character) {
        this.maze = maze;
        this.character = character;
        this.fogOfWar = new FogOfWar(maze);
    }

    public Maze getMaze() { return maze; }

    public Character getCharacter() { return character; }
    public FogOfWar getFogOfWar() { return fogOfWar; }

    public void restart() {
        maze.restart();
        character.restart();
        fogOfWar.restart();
    }
}
