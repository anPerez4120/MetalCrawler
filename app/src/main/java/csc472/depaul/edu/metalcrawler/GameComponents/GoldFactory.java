package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.Stack;

public class GoldFactory {
    Stack<Gold> golds;

    private static GoldFactory instance;
    public static GoldFactory Instance()
    {
        if (instance == null) {
            instance = new GoldFactory();
            instance.golds = new Stack<Gold>();
        }
        return instance;
    }


    public Gold GetGold(int x, int y)
    {
        if (golds.empty())
        {
            Gold gold = new Gold(GameManager.Instance().GetView(),x,y);
            return gold;
        }
        else
        {
            Gold gold= golds.pop();
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


}
