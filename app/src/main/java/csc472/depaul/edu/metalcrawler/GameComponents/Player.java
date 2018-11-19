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


    public Player(View view) {
        super(view);
        drawLayer = 1;
        isSolid = true;
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.player);
        GameManager.Instance().SetPlayer(this);
        type = EntityType.PLAYER;
        description = "This is you! A dusty, dirty adventurer seeking gold cogs to sell on the black market.";
    }

    @Override
    public void AttemptMove(int dx, int dy)
    {
        //TODO:Fix the scaling of the moves
        if (ldx == dx && ldy == dy)
        {
            //if they are riposted then make take them out of it and reset momentum
            riposte = 1;
            momentum += momentumScaling;
        }
        //riposte, if the player moves in the opposite direction of where they were initially going then give them some damage modification in the form of momentum
        else if((ldx == dx * -1 && ldy == dy) || (ldx == dx && ldy == dy * -1)){
            momentum = 1;
            //set riposte back to 1 so that they can't build up riposte by walking back and forth
            riposte = 1;
            //put them in the riposte position
            riposte += riposteScaling;
        }
        else
        {
            momentum = 1;
            riposte = 1;
        }

        ldx = dx;
        ldy = dy;

        super.AttemptMove(dx,dy);
    }

    @Override
    public float GetDamage()
    {
        return damage * momentum * riposte;
    }


    @Override
    protected void SetDrawLayer()
    {
        drawLayer = 1;
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
