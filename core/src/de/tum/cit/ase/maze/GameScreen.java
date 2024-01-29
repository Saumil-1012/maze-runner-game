package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */

public class GameScreen implements Screen, CharacterListener {
    private final OrthographicCamera camera;
    private final GameState gameState;
    private final MazeRunnerGame game;
    private float time = 0f;
    private final HUD hud;
    private final DialogService dialogService;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game, Maze maze) {
        this.game = game;
        gameState = new GameState(maze, new Character(maze, this));
        gameState.restart();
        this.hud = new HUD(game, gameState);
        this.dialogService = new DialogService(game, this);
        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.35f;
        camera.position.set(0, 0, 0);
    }

    @Override
    public void render(float delta) {
        time += delta;

        // Check for escape key press to pause game
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dialogService.showPauseDialog();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        camera.position.set(gameState.getCharacter().getPosition(), 0); // Camera follows player
        camera.update(); // Update the camera

        if (!dialogService.isDialogOpen()) {
            handleInputs();
            updateAttackingEntities(delta);
            gameState.getCharacter().checkForCollision();
            gameState.getCharacter().update(delta);
            updateFogOfWar();
        }

        // Set up and begin drawing with the sprite batch
        game.getSpriteBatch().setProjectionMatrix(camera.combined);

        game.getSpriteBatch().begin(); // Important to call this before drawing anything
        renderGroundTexture();
        drawMaze();
        drawTraps();
        drawEnemies();
        drawFog();
        drawCharacter();
        game.getSpriteBatch().end(); // Important to call this after drawing everything
        hud.render(delta);
    }

    private void drawMaze() {
        for (int x=0; x<getMaze().getM(); x++) {
            for (int y=0; y<getMaze().getN(); y++) {
                Tile tile = getMaze().getTile(x, y);
                if (tile != null) {
                    if (!tile.isRevealed) continue;
                    if ((tile.getType() == TileType.KEY || tile.getType() == TileType.SPEED) && tile.isInteracted) continue;
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

    private void drawFog() {
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
                if (gameState.getFogOfWar().isFoggy(x, y)) {
                    game.getSpriteBatch().draw(TextureProvider.getFogTexture(), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);
                }
            }
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

    private void updateFogOfWar() {
        gameState.getCharacter().updateFogOfWar(gameState.getFogOfWar());
    }

    //
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        hud.resize(width, height);
    }

    public void restart() {
        gameState.restart();
    }

    private Maze getMaze() { return gameState.getMaze(); }
    public HUD getHud() { return hud; }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void show() {
        Gdx.input.setInputProcessor(hud.getStage());
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

    @Override
    public void onCollisionWithEnemy(Enemy enemy) {
        SoundsProvider.playEnemyHitSound();
    }

    @Override
    public void onCollisionWithTrap(Trap trap) {
        SoundsProvider.playTrapTriggerSound();
    }

    @Override
    public void onCollisionWithExit() {
        if (gameState.getCharacter().hasEnoughKeys()) {
            SoundsProvider.playMazeCompleteSound();
            dialogService.showWinGameDialog();
        }
    }

    @Override
    public void onCollisionWithKey(Tile tile) {
        SoundsProvider.playKeyPickSound();
    }

    @Override
    public void onCollisionWithSpeed(Tile tile) {
        SoundsProvider.playKeyPickSound();
    }

    @Override
    public void onDead() {
        SoundsProvider.playDeathSound();
        dialogService.showLoseGameDialog();
    }
}
