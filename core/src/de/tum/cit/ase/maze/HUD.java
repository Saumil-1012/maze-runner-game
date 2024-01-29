package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HUD {
    private final GameState gameState;
    private final Stage stage;
    private final Image keyImage;
    private final Image speedImage;
    private final Label speedLabel;
    private final Image[] livesImages;

    public HUD(MazeRunnerGame game, GameState gameState) {
        this.gameState = gameState;

        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        table.bottom();
        stage.addActor(table);

        livesImages = new Image[Constants.MAX_HEALTH];
        table.add().padLeft(20);
        for (int i = 0; i < Constants.MAX_HEALTH; i++) {
            livesImages[i] = new Image(TextureProvider.getCharacterTexture());
            livesImages[i].setScale(2);
            table.add(livesImages[i]).padTop(10).padRight(17); // Add padding as needed
        }

        keyImage = new Image();
        keyImage.setScale(2);
        keyImage.setDrawable(new TransparentDrawable());
        table.add(keyImage).padLeft(20).padBottom(-20);

        speedImage = new Image();
        speedImage.setScale(2);
        speedImage.setDrawable(new TransparentDrawable());
        table.add(speedImage).padLeft(20).padBottom(-20);

        speedLabel = new Label("", game.getSkin());
        table.add(speedLabel).padLeft(30);

        table.left().bottom().padBottom(20); // Align the table to the top right
    }

    private boolean areEnoughKeys() { return gameState.getCharacter().keys >= gameState.getMaze().getTotalKeys(); }
    private boolean isSpeedBoostActive() { return gameState.getCharacter().isSpeedBoostActive(); }

    public void render(float delta) {
        for (int i = 0; i < livesImages.length; i++) {
            if (i < gameState.getCharacter().health) {
                livesImages[i].setDrawable(new TextureRegionDrawable(TextureProvider.getCharacterTexture()));
            } else {
                livesImages[i].setDrawable(new TransparentDrawable());
            }
        }

        if (areEnoughKeys()) {
            keyImage.setDrawable(new TextureRegionDrawable(TextureProvider.getTextureForTileType(TileType.KEY)));
        } else {
            keyImage.setDrawable(new TransparentDrawable());
        }

        if (isSpeedBoostActive()) {
            speedImage.setDrawable(new TextureRegionDrawable(TextureProvider.getTextureForTileType(TileType.SPEED)));
            speedLabel.setText(Math.round(gameState.getCharacter().getSpeedBoostLeftTime()) + " sec");
        } else {
            speedImage.setDrawable(new TransparentDrawable());
            speedLabel.setText("");
        }

        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public Stage getStage() { return stage; }
}
