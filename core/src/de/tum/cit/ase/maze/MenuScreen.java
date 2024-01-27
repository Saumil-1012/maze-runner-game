package de.tum.cit.ase.maze;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    private final Stage stage;
    private final Table table1;
    private final Table table2;
    private ArrayList<Maze> mazes;
    private Texture backgroundTexture;

    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public MenuScreen(MazeRunnerGame game) {
        mazes = MazeLoader.loadMazesInDir();
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        table1 = new Table(); // Create a table for layout
        table2 = new Table(); // Create a table for layout
        table1.setFillParent(true); // Make the table fill the stage
        table2.setFillParent(true); // Make the table fill the stage
        stage.addActor(table1); // Add the table to the stage
        stage.addActor(table2); // Add the table to the stage
        table2.setVisible(false);

        // Add a label as a title
        table1.add(new Label("Maze Runner", game.getSkin(), "title")).padBottom(80).row();

        // Load background image
        backgroundTexture = new Texture(Gdx.files.internal("photo1.jpg.png"));
        // Create and add a button to go to the game screen
        // Add menu buttons
        TextButton startButton = new TextButton("Start", game.getSkin());
        TextButton exitButton = new TextButton("Exit", game.getSkin());

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                table1.setVisible(false);
                table2.setVisible(true);
            }
        });
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table1.add(startButton).padBottom(20).row();
        table1.add(exitButton).padBottom(20);

        // Add a label as a title
        table2.add(new Label("Maze Runner", game.getSkin(), "title")).padBottom(80).row();

        for (int i=0; i<mazes.size(); i++) {
            final Maze maze = mazes.get(i);
            TextButton button = new TextButton("Maze " + (i+1), game.getSkin());
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setMaze(maze);
                    game.goToGame();
                }
            });

            table2.add(button).padBottom(20).row();
        }

        TextButton backButton = new TextButton("Back", game.getSkin());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                table1.setVisible(true);
                table2.setVisible(false);
            }
        });

        table2.add(backButton).padBottom(20).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events

        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
