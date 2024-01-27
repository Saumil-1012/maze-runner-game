package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */

public class GameScreen implements Screen {
    private final OrthographicCamera camera;
    private final GameState gameState;
    private BitmapFont font;
    private MazeRunnerGame game;
    private float time = 0f;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game, Maze maze) {
        this.game = game;
        gameState = new GameState(maze, new Character(maze));
        gameState.restart();
        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.5f;
        camera.position.set(0, 0, 0);
        this.font = new BitmapFont();
        /* Get the font from the game's skin */
    }


    // Screen interface methods with necessary functionality

    @Override
    public void render(float delta) {
        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
        }

        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen
        camera.position.set(gameState.getCharacter().position, 0); // Camera follows player
        camera.update(); // Update the camera

        // Set up and begin drawing with the sprite batch
        game.getSpriteBatch().setProjectionMatrix(camera.combined);

        game.getSpriteBatch().begin(); // Important to call this before drawing anything
        drawMaze();
        drawCharacter();
        game.getSpriteBatch().end(); // Important to call this after drawing everything
    }

    private void drawMaze() {
        for (int x=0; x<getMaze().getM(); x++) {
            for (int y=0; y<getMaze().getN(); y++) {
                Tile tile = getMaze().getTile(x, y);
                if (tile != null) {
                    tile.draw(game.getSpriteBatch());
                }
            }
        }
    }

    private void drawCharacter() {
        game.getSpriteBatch().draw(
                TextureProvider.getCharacterTexture(),
                gameState.getCharacter().position.x,
                gameState.getCharacter().position.y
        );
    }

    //
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
    }

    private Maze getMaze() { return gameState.getMaze(); }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}