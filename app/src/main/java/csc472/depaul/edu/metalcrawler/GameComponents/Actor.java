package csc472.depaul.edu.metalcrawler.GameComponents;

import android.util.Log;
import android.view.View;

public class Actor extends Entity implements IMoving, IDamage {
    float health = 100;
    float health_max = 100;

    float damage = 10;
    String name;
    public Actor(){}
    public Actor(View view) {
        super(view);
        GameManager.Instance().AddActor(this);
    }
    public Actor(View view, int x, int y) {
        super(view);
    }

    public void MoveUp(){       AttemptMove( 0,-1);}
    public void MoveDown(){     AttemptMove( 0, 1);}
    public void MoveLeft(){     AttemptMove(-1, 0);}
    public void MoveRight(){    AttemptMove( 1, 0);}
    public void dontMove(){ AttemptMove(0, 0);}

    public void JumpToPosition(int x, int y){this.x = x; this.y = y;}

    public void AttemptMove(int dx, int dy)
    {
        int newX = x+dx;
        int newY = y+dy;

        Tile tile = GameManager.Instance().GetTile(newX,newY);

        if (tile == null) // We dont care about null tiles rn
        {
            // Move(dx,dy);
        }
        else {
            Entity entity = tile.GetEntity();
            if (entity != null && !entity.equals(this)) { // Is there an entity there, and is it not itself
                entity.OnTouched(this); // Perform its onTouch
                if (tile.GetEntity() == null || !entity.IsSolid()) { // Is it flaccid :)))))) wait why did android studio spell-check me on the word 'flaccid'
                    Move(dx,dy);
                }
            } else {
                Move(dx,dy);
            }
        }
    }

    public void Move(int dx, int dy)
    {
        int oldX = x;
        int oldY = y;
        x += dx;
        y += dy;
        GameManager.Instance().GetCurrentEnvironment().HookUpTile(oldX,oldY,x,y,this);
    }
    // TODO: Daamge
    public void Damage(float damage){
        health -= damage;
        if (health <= 0)
            Die();
    }
    public void Heal(float heal){
        health += heal;
        if (health > health_max){
            health = health_max;
        }
    }
    public void RestoreHealth(){ health = health_max;}
    public void Die(){GameManager.Instance().GetCurrentEnvironment().HookUpTile(x,y,-1,-1,this);}//GameManager.Instance().RemoveSprite(this);GameManager.Instance().RemoveActor(this);}

    public float GetDamage()
    {

        System.out.println(type.toString() + " dealt " + Float.toString(this.damage) + " damage");
        Log.d("",type.toString() + " dealt " + Float.toString(this.damage) + " damage");
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

    public void Update()
    {
        // Do Nothing
    }


    @Override
    public void OnTouched(Actor other)
    {
        Damage(other.GetDamage());
    }

    @Override
    public String GetDescription()
    {
        return GetInfo() + description;
    }

    public String GetInfo()
    {
        return String.format("%s\nHealth: %.2f/%.2f\nBase Damage: %.2f\n\n", name, health, health_max, damage);
    }
}
