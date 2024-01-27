package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
Provides texture for rendering
 */
public class TextureProvider {
    private static TextureRegion tGround;
    private static TextureRegion tWall;
    private static TextureRegion tEntry;
    private static TextureRegion tExit;
    private static TextureRegion tTrap;
    private static TextureRegion tKey;

    private static TextureRegion tCharacter;
    private static TextureRegion tEnemy;

    public static void init() {
        Texture basicTiles = new Texture(Gdx.files.internal("basictiles.png"));
        Texture objects = new Texture(Gdx.files.internal("objects.png"));
        Texture mobs = new Texture(Gdx.files.internal("mobs.png"));

        tGround = extractTextureRegion(basicTiles,0,8);
        tWall = extractTextureRegion(basicTiles,7,1);
        tEntry = extractTextureRegion(basicTiles,6,6);
        tExit = extractTextureRegion(basicTiles,1,6);
        tTrap = extractTextureRegion(basicTiles,1,4);
        tKey = extractTextureRegion(objects,0,4);
        tCharacter = extractTextureRegion(mobs, 4, 0);
        tEnemy = extractTextureRegion(mobs, 10, 0);
    }

    private static TextureRegion extractTextureRegion(Texture texture, int x, int y) {
        return new TextureRegion(texture, x*Constants.TILE_SIZE, y*Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public static TextureRegion getTextureForTileType(TileType type) {
        switch (type) {
            case WALL: return tWall;
            case ENTRY: return tEntry;
            case EXIT: return tExit;
            case TRAP: return tTrap;
            case ENEMY: return tEnemy;
            case KEY: return tKey;
        }
        return null;
    }

    public static TextureRegion getCharacterTexture() {
        return tCharacter;
    }
}
