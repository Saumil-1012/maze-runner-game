package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundsProvider {
    private static Music keyPickSound;
    private static Music deathSound;
    private static Music trapTriggerSound;
    private static Music enemyHitSound;
    private static Music mazeCompleteSound;

    public static void init() {
        keyPickSound = Gdx.audio.newMusic(Gdx.files.internal("key_pick.mp3"));
        deathSound = Gdx.audio.newMusic(Gdx.files.internal("death.mp3"));
        trapTriggerSound = Gdx.audio.newMusic(Gdx.files.internal("trap_hit.mp3"));
        enemyHitSound = Gdx.audio.newMusic(Gdx.files.internal("enemy_hit.mp3"));
        mazeCompleteSound = Gdx.audio.newMusic(Gdx.files.internal("maze_complete.mp3"));
    }

    public static void playKeyPickSound() { keyPickSound.play(); }
    public static void playDeathSound() { deathSound.play(); }
    public static void playTrapTriggerSound() { trapTriggerSound.play(); }
    public static void playEnemyHitSound() { enemyHitSound.play(); }
    public static void playMazeCompleteSound() { mazeCompleteSound.play(); }
}
