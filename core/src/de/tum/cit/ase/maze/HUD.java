package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD{
    private BitmapFont font;
    private int lives;
    private boolean hasKey;

    public HUD() {
        // Initialize font and other HUD elements
        font = new BitmapFont();
        lives = 3; // Set an initial number of lives
        hasKey = false; // Initially, the player has not collected the key
    }

    // Update HUD based on game events
    public void update(int lives, boolean hasKey) {
        this.lives = lives;
        this.hasKey = hasKey;
    }

    // Draw HUD on the screen
    public void draw(SpriteBatch batch) {
        // Use batch to draw HUD elements
        font.draw(batch, "Lives: " + lives, 20, 460);
        font.draw(batch, "Key: " + (hasKey ? "Collected" : "Not Collected"), 20, 440);
    }

    // Dispose of resources when the game is over
    public void dispose() {
        font.dispose();
    }
}

