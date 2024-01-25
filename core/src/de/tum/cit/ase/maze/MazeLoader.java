package de.tum.cit.ase.maze;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MazeLoader  implements Screen {
    
        private HUD hud;
    private SpriteBatch batch;

    // ... Other class members and methods ...

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

    // ... Other methods...

        @Override
        public void dispose() {
            // Dispose of HUD resources when the screen is disposed
            hud.dispose();
        }
    

    private int currentMapIndex;
    private String[] mapFiles = {"maps/maze1.txt", "maps/maze2.txt", "maps/maze3.txt"};

    public MazeLoader() {
        currentMapIndex = 0;
    }

    public void loadNextMap(String mapFile) {
        if (currentMapIndex < mapFiles.length) {
            loadNextMap(mapFiles[currentMapIndex]);
            currentMapIndex++;
        } else {
            // All maps loaded, you may want to handle this situation
        }
    }
}
