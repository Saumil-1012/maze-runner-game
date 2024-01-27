package de.tum.cit.ase.maze;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Key {
    private Vector2 position;
    private TextureRegion textureRegion;

    public Key(float initialX, float initialY, TextureRegion textureRegion) {
        this.position = new Vector2(initialX, initialY);
        this.textureRegion = textureRegion;
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, position.x, position.y);
    }

    // Implement other key-specific logic
}

