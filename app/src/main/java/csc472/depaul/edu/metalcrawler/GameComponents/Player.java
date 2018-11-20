package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import csc472.depaul.edu.metalcrawler.R;

public class Player extends Actor {
    float momentum = 1;
    float momentumScaling = 1;
    float riposte = 1;
    float riposteScaling = 1;
    int ldx = 0;
    int ldy = 0;


    float riposteAmount = 2.111f;

    public Player(View view) {
        super(view);
        drawLayer = 1;
        isSolid = true;
        name = "You";
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.player);
        GameManager.Instance().SetPlayer(this);
        type = EntityType.PLAYER;
        description = "This is you! A dusty, dirty adventurer seeking gold cogs to sell on the black market.";
    }

    @Override
    public void AttemptMove(int dx, int dy)
    {
        int xx = x;
        int yy = y;


        //riposte, if the player moves in the opposite direction of where they were initially going then give them some damage modification in the form of momentum
        if ((ldx != 0 && ldx == -dx) || (ldy != 0 && ldy == -dy)) {
            //put them in the riposte position
            riposte = true;
        } else {
            riposte = false;
        }

        if (ldx == dx && ldy == dy) {
        }
        else
        {
            momentum = 1;
        }

        // SUPER ATTEMPT MOVE
        int newX = x+dx;
        int newY = y+dy;

        Tile tile = GameManager.Instance().GetTile(newX,newY);

        if (tile == null) // We dont care about null tiles rn
        {
            riposte = false;
            momentum = 1;
        }
        else {
            Entity entity = tile.GetEntity();
            if (entity != null) { // Is there an entity there
                entity.OnTouched(this); // Perform its onTouch
                if (tile.GetEntity() == null || !entity.IsSolid()) { // Is it flaccid :)))))) wait why did android studio spell-check me on the word 'flaccid'
                    Move(dx,dy);
                }
                else
                {
                    riposte = false;
                    momentum = 1;
                }
            } else {
                Move(dx,dy);
            }
        }
        // SUPER ATTEMPT MOVE

        if (xx != x || yy != y) {
            if (ldx == dx && ldy == dy) {
                //if they are riposted then make take them out of it and reset momentum
                if (riposte) {
                    riposte = false;
                }
                momentum += 1;
                if (momentum > 5)
                    momentum = 5;
            }
            else
            {
                momentum = 1;
            }
        }
        else
        {
            momentum = 1;
        }
        ldx = dx;
        ldy = dy;

    }

    @Override
    public float GetDamage()
    {
        float tDamage = damage * momentum;
        if (riposte)
        {
            tDamage = damage * riposteAmount;
            riposte = false;
        }

        System.out.println(type.toString() + " dealt " + Float.toString(tDamage) + " damage");
        Log.d("",type.toString() + " dealt " + Float.toString(tDamage) + " damage");
        return tDamage;
    }




    @Override
    protected void SetDrawLayer()
    {
        drawLayer = 1;
    }

    @Override
    public String GetDescription()
    {
        String info = GetInfo();

        info += "Current MOMENTUM Multiplier: " + Float.toString(momentum);
        info += "\n- Potential MOMENTUM damage: " + Float.toString(damage * momentum);
        info += "\nCurrent RIPOSTE Multiplier: " + Float.toString(riposteAmount);
        info += "\n- Potential RIPOSTE damage: " + Float.toString(damage * riposteAmount);

        info += "\n\n";

        return info + description;
    }
    public void setClass(PlayerClass c){
        health = c.GetMaxHealth();
        health_max = c.GetMaxHealth();
        momentumScaling = c.GetMomentumScaling();
        riposteScaling = c.GetRiposteScaling();
    }


    @Override
    public void PrintEntity()
    {
        System.out.println("Player");
        Log.d("Player","Player");
    }
}
