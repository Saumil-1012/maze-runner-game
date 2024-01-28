package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

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
    private static Animation<TextureRegion> aCharacterUpAnimation;
    private static Animation<TextureRegion> aCharacterRightAnimation;
    private static Animation<TextureRegion> aCharacterDownAnimation;
    private static Animation<TextureRegion> aCharacterLeftAnimation;
    private static Animation<TextureRegion> aEnemyUpAnimation;
    private static Animation<TextureRegion> aEnemyRightAnimation;
    private static Animation<TextureRegion> aEnemyDownAnimation;
    private static Animation<TextureRegion> aEnemyLeftAnimation;
    private static Animation<TextureRegion> aKeyAnimation;
    private static Animation<TextureRegion> aFireAnimation;

    public static void init() {
        Texture basicTiles = new Texture(Gdx.files.internal("basictiles.png"));
        Texture objects = new Texture(Gdx.files.internal("objects.png"));
        Texture mobs = new Texture(Gdx.files.internal("mobs.png"));

        tGround = extractTextureRegion(basicTiles,2,1);
        tWall = extractTextureRegion(basicTiles,7,1);
        tEntry = extractTextureRegion(basicTiles,6,6);
        tExit = extractTextureRegion(basicTiles,1,6);
        tTrap = extractTextureRegion(basicTiles,2,9);
        tKey = extractTextureRegion(objects,0,4);
        tCharacter = extractTextureRegion(mobs, 4, 0);
        tEnemy = extractTextureRegion(mobs, 10, 0);

        loadCharacterAnimation(mobs);
        loadEnemyAnimation(mobs);
        loadOtherAnimations(objects);
    }

    private static void loadCharacterAnimation(Texture mobTexture) {
        int frameWidth = 16;
        int frameHeight = 16;

        Array<TextureRegion> walkFramesDown = new Array<>(TextureRegion.class);
        for (int col : new int[]{0, 1, 2, 1}) {
            walkFramesDown.add(new TextureRegion(mobTexture, (3+col) * frameWidth, 0, frameWidth, frameHeight));
        }
        aCharacterDownAnimation = new Animation<>(0.1f, walkFramesDown);

        Array<TextureRegion> walkFramesLeft = new Array<>(TextureRegion.class);
        for (int col : new int[]{0, 1, 2, 1}) {
            walkFramesLeft.add(new TextureRegion(mobTexture, (3+col) * frameWidth, frameHeight, frameWidth, frameHeight));
        }
        aCharacterLeftAnimation = new Animation<>(0.1f, walkFramesLeft);

        Array<TextureRegion> walkFramesRight = new Array<>(TextureRegion.class);
        for (int col : new int[]{0, 1, 2, 1}) {
            walkFramesRight.add(new TextureRegion(mobTexture, (3+col) * frameWidth, 2*frameHeight, frameWidth, frameHeight));
        }
        aCharacterRightAnimation = new Animation<>(0.1f, walkFramesRight);

        Array<TextureRegion> walkFramesUp = new Array<>(TextureRegion.class);
        for (int col : new int[]{0, 1, 2, 1}) {
            walkFramesUp.add(new TextureRegion(mobTexture, (3+col) * frameWidth, 3*frameHeight, frameWidth, frameHeight));
        }
        aCharacterUpAnimation = new Animation<>(0.1f, walkFramesUp);
    }

    private static void loadEnemyAnimation(Texture mobTexture) {
        int frameWidth = 16;
        int frameHeight = 16;

        Array<TextureRegion> walkFramesDown = new Array<>(TextureRegion.class);
        for (int col : new int[]{0, 1, 2, 1}) {
            walkFramesDown.add(new TextureRegion(mobTexture, (9+col) * frameWidth, 0, frameWidth, frameHeight));
        }
        aEnemyDownAnimation = new Animation<>(0.1f, walkFramesDown);

        Array<TextureRegion> walkFramesLeft = new Array<>(TextureRegion.class);
        for (int col : new int[]{0, 1, 2, 1}) {
            walkFramesLeft.add(new TextureRegion(mobTexture, (9+col) * frameWidth, frameHeight, frameWidth, frameHeight));
        }
        aEnemyLeftAnimation = new Animation<>(0.1f, walkFramesLeft);

        Array<TextureRegion> walkFramesRight = new Array<>(TextureRegion.class);
        for (int col : new int[]{0, 1, 2, 1}) {
            walkFramesRight.add(new TextureRegion(mobTexture, (9+col) * frameWidth, 2*frameHeight, frameWidth, frameHeight));
        }
        aEnemyRightAnimation = new Animation<>(0.1f, walkFramesRight);

        Array<TextureRegion> walkFramesUp = new Array<>(TextureRegion.class);
        for (int col : new int[]{0, 1, 2, 1}) {
            walkFramesUp.add(new TextureRegion(mobTexture, (9+col) * frameWidth, 3*frameHeight, frameWidth, frameHeight));
        }
        aEnemyUpAnimation = new Animation<>(0.1f, walkFramesUp);
    }

    private static void loadOtherAnimations(Texture objectTexture) {
        int frameWidth = 16;
        int frameHeight = 16;

        Array<TextureRegion> keyAnimationFrames = new Array<>(TextureRegion.class);
        for (int col : new int[]{0, 1, 2, 1}) {
            keyAnimationFrames.add(new TextureRegion(objectTexture, col * frameWidth, 4*frameHeight, frameWidth, frameHeight));
        }
        aKeyAnimation = new Animation<>(0.1f, keyAnimationFrames);

        frameWidth = 32;
        frameHeight = 23;
        Array<TextureRegion> fireAnimationFrames = new Array<>(TextureRegion.class);
        for (int col=0; col < 8; col++) {
            fireAnimationFrames.add(new TextureRegion(objectTexture, 223 + (col * frameWidth), 39, frameWidth, frameHeight));
        }
        aFireAnimation = new Animation<>(0.1f, fireAnimationFrames);
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

    public static TextureRegion getCharacterTexture() { return tCharacter; }
    public static TextureRegion getEnemyTexture() { return tEnemy; }
    public static TextureRegion getCharacterTexture(Direction direction, float time) {
        float keyFrame = time/2;
        return switch (direction) {
            case DOWN -> aCharacterDownAnimation.getKeyFrame(keyFrame, true);
            case LEFT -> aCharacterLeftAnimation.getKeyFrame(keyFrame, true);
            case RIGHT -> aCharacterRightAnimation.getKeyFrame(keyFrame, true);
            case UP -> aCharacterUpAnimation.getKeyFrame(keyFrame, true);
        };
    }
    public static TextureRegion getEnemyTexture(Direction direction, float time) {
        float keyFrame = time/2;
        return switch (direction) {
            case DOWN -> aEnemyDownAnimation.getKeyFrame(keyFrame, true);
            case LEFT -> aEnemyLeftAnimation.getKeyFrame(keyFrame, true);
            case RIGHT -> aEnemyRightAnimation.getKeyFrame(keyFrame, true);
            case UP -> aEnemyUpAnimation.getKeyFrame(keyFrame, true);
        };
    }
    public static TextureRegion getKeyTexture(float time) {
        float keyFrame = time/2;
        return aKeyAnimation.getKeyFrame(keyFrame, true);
    }
    public static TextureRegion getFireTexture(float time) {
        float keyFrame = time/2;
        return aFireAnimation.getKeyFrame(keyFrame, true);
    }

    public static TextureRegion getGroundTexture() { return tGround; }
}
