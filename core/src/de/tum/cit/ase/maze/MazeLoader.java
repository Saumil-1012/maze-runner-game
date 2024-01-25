package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.Map;
import java.util.Properties;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MazeLoader extends ScreenAdapter {
    private final Game game;
    private Stage stage;

    public MazeLoader(MazeRunnerGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        // Get the list of map files from the maps directory
        FileHandle mapsDirectory = Gdx.files.internal("maps");
        FileHandle[] mapFiles = mapsDirectory.list("properties");

        // Create buttons for each map file
        VerticalGroup buttonGroup = new VerticalGroup();
        TextButton mapButton= new TextButton("select", game.getSkin() );
        for (final FileHandle mapFile : mapFiles) {
            ClassValue<Object> YourSkinClass = null;
            mapButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Load the selected map and switch to the GameScreen
                    try {
                        maze.wait(Long.parseLong(mapFile.path()));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    game.setScreen(new GameScreen(game));
                }
            });
            buttonGroup.addActor(mapButton);
        }

        // Add the buttons to the stage or your UI table
        stage.addActor(buttonGroup);
    }

    // ... other lifecycle methods (render, resize, dispose) go here


        private HUD hud;
        private SpriteBatch batch;

        private int[][] maze; // Represents the maze where each value corresponds to a specific object type

        public void loadMaze(String fileName) {
            FileHandle fileHandle = Gdx.files.internal("maps/" + fileName);

            if (fileHandle.exists()) {
                Properties properties = new Properties();
                try {
                    properties.load(fileHandle.reader());
                    parseProperties(properties);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Gdx.app.error("MazeLoader", "File not found: " + fileName);
            }
        }

        private void parseProperties(Properties properties) {
            int width = Integer.parseInt(properties.getProperty("width", "0"));
            int height = Integer.parseInt(properties.getProperty("height", "0"));
            maze = new int[width][height];

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();

                if (key.matches("\\d+,\\d+")) { // Check if the key is in the format "x,y"
                    String[] coordinates = key.split(",");
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    int objectType = Integer.parseInt(value);

                    if (x < width && y < height) {
                        maze[x][y] = objectType;
                    }
                }
            }
        }
        @Override
        public void show() {
            // Initialize HUD
            hud = new HUD();
        }
        @Override
        public void render(float delta) {
            // Update HUD based on game events (e.g., player's lives, key collection)
            boolean hasKey;
            hasKey = false;
            int playerLives = 0;
            hud.update(playerLives, hasKey);

            // Render the game and HUD
            batch.begin();
            // ... Render the maze, characters, etc.
            batch.end();

            // Draw the HUD on top of the game
            hud.draw(batch);
        }
    @Override
    public void resize(int width, int height) {
        
    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }
        @Override
        public void dispose() {
            // Dispose of HUD resources when the screen is disposed
            hud.dispose();
        }
    

    private int currentMapIndex;
    private String[] mapFiles = {"maps/maze1.txt", "maps/maze2.txt", "maps/maze3.txt"};

    public void loadNextMap(String mapFile) {
        if (currentMapIndex < mapFiles.length) {
            loadNextMap(mapFiles[currentMapIndex]);
            currentMapIndex++;
        } else {
            // All maps loaded, you may want to handle this situation
        }
    }
}
