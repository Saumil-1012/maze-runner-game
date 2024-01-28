package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
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
    private HUD hud;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game, Maze maze) {
        this.game = game;
        gameState = new GameState(maze, new Character(maze));
        gameState.restart();
        this.hud = new HUD(game, gameState);
        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.5f;
        camera.position.set(0, 0, 0);
        this.font = new BitmapFont();
        /* Get the font from the game's skin */
    }

    @Override
    public void render(float delta) {
        time += delta;

        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        camera.position.set(gameState.getCharacter().getPosition(), 0); // Camera follows player
        camera.update(); // Update the camera
        handleInputs();
        updateAttackingEntities(delta);
        gameState.getCharacter().checkForCollision();

        // Set up and begin drawing with the sprite batch
        game.getSpriteBatch().setProjectionMatrix(camera.combined);

        game.getSpriteBatch().begin(); // Important to call this before drawing anything
        renderGroundTexture();
        drawMaze();
        drawTraps();
        drawEnemies();
        drawCharacter();
        game.getSpriteBatch().end(); // Important to call this after drawing everything
        hud.render(delta);
    }

    private void drawMaze() {
        for (int x=0; x<getMaze().getM(); x++) {
            for (int y=0; y<getMaze().getN(); y++) {
                Tile tile = getMaze().getTile(x, y);
                if (tile != null) {
                    if (tile.getType() == TileType.KEY && tile.isInteracted) continue;
                    tile.draw(game.getSpriteBatch(), time);
                } else {
                    game.getSpriteBatch().draw(
                            TextureProvider.getGroundTexture(),
                            x*Constants.TILE_SIZE,
                            y*Constants.TILE_SIZE
                    );
                }
            }
        }
    }

    private void renderGroundTexture() {
        float startX = camera.position.x - camera.viewportWidth / 2;
        float startY = camera.position.y - camera.viewportHeight / 2;
        float endX = startX + camera.viewportWidth;
        float endY = startY + camera.viewportHeight;

        // Convert to tile coordinates
        int tileStartX = (int) (startX / Constants.TILE_SIZE);
        int tileStartY = (int) (startY / Constants.TILE_SIZE);
        int tileEndX = (int) (endX / Constants.TILE_SIZE) + 1;
        int tileEndY = (int) (endY / Constants.TILE_SIZE) + 1;

        for (int x = tileStartX; x < tileEndX; x++) {
            for (int y = tileStartY; y < tileEndY; y++) {
                game.getSpriteBatch().draw(TextureProvider.getGroundTexture(), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);
            }
        }
    }

    private void drawTraps() {
        for (Trap trap : getMaze().getTraps()) {
            game.getSpriteBatch().draw(
                    TextureProvider.getTextureForTileType(TileType.TRAP),
                    trap.getX(),
                    trap.getY()
            );
            game.getSpriteBatch().draw(
                    TextureProvider.getFireTexture(time),
                    trap.getX(),
                    trap.getY(),
                    Constants.TILE_SIZE,
                    Constants.TILE_SIZE
            );
        }
    }

    private void drawEnemies() {
        for (Enemy enemy : getMaze().getEnemies()) {
            game.getSpriteBatch().draw(
                    TextureProvider.getEnemyTexture(enemy.getLastDirection(), time),
                    enemy.getPosition().x,
                    enemy.getPosition().y
            );
        }
    }

    private void drawCharacter() {
        game.getSpriteBatch().draw(
                TextureProvider.getCharacterTexture(gameState.getCharacter().getLastDirection(), time),
                gameState.getCharacter().getPosition().x,
                gameState.getCharacter().getPosition().y
        );
    }

    private void handleInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            gameState.getCharacter().move(Direction.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            gameState.getCharacter().move(Direction.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            gameState.getCharacter().move(Direction.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            gameState.getCharacter().move(Direction.DOWN);
        }
    }

    private void updateAttackingEntities(float delta) {
        for (Enemy enemy : getMaze().getEnemies()) {
            enemy.update(delta);
        }
        for (Trap trap : getMaze().getTraps()) {
            trap.update(delta);
        }
    }

    //
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        hud.resize(width, height);
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
