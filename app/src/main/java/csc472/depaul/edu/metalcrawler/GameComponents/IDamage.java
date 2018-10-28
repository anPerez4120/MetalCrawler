package csc472.depaul.edu.metalcrawler.GameComponents;

public interface IDamage {
    float health = 100;
    float health_max = 100;

    void Damage(float damage);
    void Heal(float healing);
    void RestoreHealth();
    void Die();
}
