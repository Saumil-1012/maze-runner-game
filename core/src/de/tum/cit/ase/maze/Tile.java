package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile extends Collider {
    int x, y;
    private TileType type;
    public boolean isInteracted = false;
    public boolean isPartiallyRevealed = false;
    public boolean isRevealed = false;

    public Tile(int x, int y, TileType type) {
        super(new Rectangle(x*Constants.TILE_SIZE, y*Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE));

        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(SpriteBatch batch, float time) {
        TextureRegion texture = TextureProvider.getTextureForTileType(type);
        if (type == TileType.KEY) {
            texture = TextureProvider.getKeyTexture(time);
        }
        if (texture == null) return;
        batch.draw(
                texture,
                x * Constants.TILE_SIZE,
                y * Constants.TILE_SIZE
        );
    }

    public TileType getType() { return type; }
}
