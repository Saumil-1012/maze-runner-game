package de.tum.cit.ase.maze;

public class AttackingEntity {
    private final float ATTACK_COOLDOWN = 2f;
    private float timePassedSinceAttack = ATTACK_COOLDOWN;

    public boolean canAttack() {
        return timePassedSinceAttack > ATTACK_COOLDOWN;
    }
    public void attack() {
        if (canAttack()) {
            timePassedSinceAttack = 0f;
        }
    }

    public void update(float delta) {
        if (canAttack()) return;
        timePassedSinceAttack += delta;
    }
}
