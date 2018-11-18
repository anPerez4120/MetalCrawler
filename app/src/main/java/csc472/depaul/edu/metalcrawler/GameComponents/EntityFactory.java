package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.Stack;

public class EntityFactory {
    Stack<Gold> golds;
    Stack<Potion> potions;

    private static EntityFactory instance;
    public static EntityFactory Instance()
    {
        if (instance == null) {
            instance = new EntityFactory();
            instance.golds = new Stack<Gold>();
            instance.potions = new Stack<Potion>();
        }
        return instance;
    }


    public Gold GetGold(int x, int y)
    {
        if (golds.empty())
        {
            Gold gold = new Gold(GameManager.Instance().GetView(),x,y);
            GameManager.Instance().AddEntity(gold);
            return gold;
        }
        else
        {
            Gold gold= golds.pop();
            GameManager.Instance().AddEntity(gold);
            GameManager.Instance().AddSprite(gold);
            gold.SetPosition(x,y);
            return gold;
        }
    }

    public void ReturnGold(Gold gold)
    {
        GameManager.Instance().RemoveSprite(gold);
        golds.push(gold);
    }


    public Potion GetPotion(int x, int y)
    {
        if (potions.empty())
        {
            Potion potion = new Potion(GameManager.Instance().GetView(),x,y);
            GameManager.Instance().AddEntity(potion);
            return potion;
        }
        else
        {
            Potion potion= potions.pop();
            GameManager.Instance().AddEntity(potion);
            GameManager.Instance().AddSprite(potion);
            potion.SetPosition(x,y);
            return potion;
        }
    }

    public void ReturnPotion(Potion potion)
    {
        GameManager.Instance().RemoveSprite(potion);
        potions.push(potion);
    }
}
