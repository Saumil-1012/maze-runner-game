package de.tum.cit.ase.maze;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class DialogService {
    private final MazeRunnerGame game;
    private final GameScreen gameScreen;
    private boolean isDialogOpen = false;

    public DialogService(MazeRunnerGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
    }

    public void showWinGameDialog() {
        showEndGameDialog("Maze Completed");
    }

    public void showLoseGameDialog() {
        showEndGameDialog("You died");
    }

    private void showEndGameDialog(String message) {
        if (isDialogOpen) return;
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                isDialogOpen = false;
                switch ((int)object) {
                    case 1:
                        gameScreen.restart();
                        break;
                    case 2:
                        game.goToMenu();
                        break;
                }
            }
        };

        dialog.text(message).row();
        dialog.button(new TextButton("Restart", game.getSkin()), 1).row();
        dialog.button(new TextButton("Menu", game.getSkin()), 2).row();
        dialog.show(gameScreen.getHud().getStage());
        isDialogOpen = true;
    }

    public void showPauseDialog() {
        if (isDialogOpen) return;
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                isDialogOpen = false;
                switch ((int)object) {
                    case 1:
                        gameScreen.restart();
                        break;
                    case 2:
                        game.goToMenu();
                        break;
                }
            }
        };

        dialog.text("Game Paused").row();
        dialog.button(new TextButton("Resume", game.getSkin()), 0).row();
        dialog.button(new TextButton("Restart", game.getSkin()), 1).row();
        dialog.button(new TextButton("Menu", game.getSkin()), 2).row();
        dialog.show(gameScreen.getHud().getStage());
        isDialogOpen = true;
    }

    public boolean isDialogOpen() { return isDialogOpen; }
}
