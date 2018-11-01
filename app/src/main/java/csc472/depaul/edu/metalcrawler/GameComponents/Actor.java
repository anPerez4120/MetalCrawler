package csc472.depaul.edu.metalcrawler.GameComponents;

import android.view.View;

public class Actor extends Entity implements IMoving, IDamage {

    float health = 100;
    float health_max = 100;

    float damage = 10;

    public Actor(View view) {
        super(view);
    }

    // TODO: Move
    public void MoveUp(){ y -= 1;}
    public void MoveDown(){ y += 1;}
    public void MoveLeft(){ x -= 1;}
    public void MoveRight(){ x += 1;}
    public void JumpToPosition(int x, int y){this.x = x; this.y = y;}

    // TODO: Daamge
    public void Damage(float damage){};
    public void Heal(float heal){};
    public void RestoreHealth(){};
    public void Die(){}

    public float GetDamage()
    {
        return this.damage;
    }

    public float GetHealth()
    {
        return this.health;
    }

    public float GetMaxHealth()
    {
        return this.health_max;
    }



}
