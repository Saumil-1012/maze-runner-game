package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tile {
    int x, y;
    private TileType type;

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion texture = TextureProvider.getTextureForTileType(type);
        if (texture == null) return;
        batch.draw(
                texture,
                x * Constants.TILE_SIZE,
                y * Constants.TILE_SIZE
        );
    }

    public TileType getType() { return type; }
}
