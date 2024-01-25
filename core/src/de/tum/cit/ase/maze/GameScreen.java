package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {
    private final OrthographicCamera camera;
    private BitmapFont font;
    private MazeRunnerGame game;
    private float sinusInput = 0f;

    //
    private Object keyCollectedSound;
    private Object lifeLostSound;
    private Object backgroundMusic;
    private Object gameOverSound;
    private Object victorySound;
    //

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game) {

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.75f;
        this.game = game;
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

        camera.update(); // Update the camera

        // Move text in a circular path to have an example of a moving object
        sinusInput += delta;
        float textX = (float) (camera.position.x + Math.sin(sinusInput) * 100);
        float textY = (float) (camera.position.y + Math.cos(sinusInput) * 100);

        // Set up and begin drawing with the sprite batch
        game.getSpriteBatch().setProjectionMatrix(camera.combined);

        game.getSpriteBatch().begin(); // Important to call this before drawing anything

        // Render the text
        font.draw(game.getSpriteBatch(), "Press ESC to go to menu", textX, textY);

        // Draw the character next to the text :) / We can reuse sinusInput here
        game.getSpriteBatch().draw(
                game.getCharacterDownAnimation().getKeyFrame(sinusInput, true),
                textX - 96,
                textY - 64,
                64,
                128
        );

        game.getSpriteBatch().end(); // Important to call this after drawing everything
    }


    //
    private void loadSounds() {
        AssetManager assetManager = new AssetManager();
        assetManager.load("background_music.mp3", Music.class);
        assetManager.load("life_lost_sound.mp3", Sound.class);
        assetManager.load("key_collected_sound.mp3", Sound.class);
        assetManager.load("victory_sound.mp3", Sound.class);
        assetManager.load("game_over_sound.mp3", Sound.class);
        assetManager.finishLoading();

        backgroundMusic = assetManager.get("background_music.mp3");
        lifeLostSound = assetManager.get("life_lost_sound.mp3");
        keyCollectedSound = assetManager.get("key_collected_sound.mp3");
        victorySound = assetManager.get("victory_sound.mp3");
        gameOverSound = assetManager.get("game_over_sound.mp3");
    }



    //
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    // Additional methods and logic can be added as needed for the game screen
}
