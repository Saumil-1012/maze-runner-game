package de.tum.cit.ase.maze;

public enum TileType {
    WALL,
    ENTRY,
    EXIT,
    ENEMY,
    TRAP,
    KEY,
    SPEED;

    public static TileType parseString(String type) {
        switch (type.trim()) {
            case "0": return TileType.WALL;
            case "1": return TileType.ENTRY;
            case "2": return TileType.EXIT;
            case "3": return TileType.TRAP;
            case "4": return TileType.ENEMY;
            case "5": return TileType.KEY;
            case "6": return TileType.SPEED;
        }
        return null;
    }
}
