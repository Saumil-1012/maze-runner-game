package de.tum.cit.ase.maze;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

public class MazeLoader {
    public static Maze loadMazeFromPath(String path) {
        Properties properties = new Properties();
        Maze maze = new Maze();

        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
            for (String key : properties.stringPropertyNames()) {
                try {
                    int x = Integer.parseInt(key.split(",")[0]);
                    int y = Integer.parseInt(key.split(",")[1]);
                    TileType type = TileType.parseString(properties.getProperty(key));
                    if (type == TileType.ENEMY) {
                        maze.addEnemy(new Enemy(x, y, maze));
                    } else if (type == TileType.TRAP) {
                        maze.addTrap(new Trap(x, y));
                    } else {
                        Tile tile = new Tile(x, y, type);
                        maze.setTile(x, y, tile);
                    }
                } catch (Exception e) {}
            }
        } catch (Exception e) {
            return null;
        }

        return maze;
    }

    // Loads mazes from "/maps/*.properties" files
    public static ArrayList<Maze> loadMazesInDir() {
        ArrayList<Maze> mazes = new ArrayList<>();
        String workingDirectory = System.getProperty("user.dir");
        File mapsDirectory = new File(workingDirectory, "maps");
        for (File file : mapsDirectory.listFiles()) {
            if (file.getAbsolutePath().endsWith(".properties")) {
                mazes.add(loadMazeFromPath(file.getAbsolutePath()));
            }
        }
        return mazes;
    }

    public static Maze loadMazeFromSelectDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Maze Levels (Properties)", "properties"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int selection = fileChooser.showOpenDialog(null);
        if (selection == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            return loadMazeFromPath(path);
        }
        return null;
    }
}
