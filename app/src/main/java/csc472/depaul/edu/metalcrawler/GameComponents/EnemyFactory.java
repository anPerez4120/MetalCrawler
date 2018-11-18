package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.Stack;

public class EnemyFactory {
    Stack<Junkie> junkies;
    Stack<Bull> bulls;
    Stack<Alchemist> alchemists;

    private static EnemyFactory instance;
    public static EnemyFactory Instance()
    {
        if (instance == null) {
            instance = new EnemyFactory();
            instance.junkies = new Stack<Junkie>();
            instance.bulls = new Stack<Bull>();
            instance.alchemists = new Stack<Alchemist>();
        }
        return instance;
    }


    public Junkie GetJunkie(int x, int y)
    {
        if (junkies.empty())
        {
            Junkie junkie = new Junkie(GameManager.Instance().GetView(),x,y);
            return junkie;
        }
        else
        {
            Junkie junkie = junkies.pop();
            GameManager.Instance().AddSprite(junkie);
            GameManager.Instance().AddActor(junkie);
            junkie.SetPosition(x,y);
            return junkie;
        }
    }

    public void ReturnJunkie(Junkie junkie)
    {

        GameManager.Instance().RemoveSprite(junkie);
        GameManager.Instance().RemoveActor(junkie);
        junkies.push(junkie);
    }


    public Bull GetBull(int x, int y)
    {
        if (bulls.empty())
        {
            Bull bull = new Bull(GameManager.Instance().GetView(),x,y);
            return bull;
        }
        else
        {
            Bull bull = bulls.pop();
            GameManager.Instance().AddSprite(bull);
            GameManager.Instance().AddActor(bull);
            bull.SetPosition(x,y);
            return bull;
        }
    }

    public void ReturnBull(Bull bull)
    {

        GameManager.Instance().RemoveSprite(bull);
        GameManager.Instance().RemoveActor(bull);
        bulls.push(bull);
    }


    public Alchemist GetAlchemist(int x, int y)
    {
        if (alchemists.empty())
        {
            Alchemist alchemist = new Alchemist(GameManager.Instance().GetView(),x,y);
            return alchemist;
        }
        else
        {
            Alchemist alchemist = alchemists.pop();
            GameManager.Instance().AddSprite(alchemist);
            GameManager.Instance().AddActor(alchemist);
            alchemist.SetPosition(x,y);
            return alchemist;
        }
    }

    public void ReturnAlchemist(Alchemist alchemist)
    {
        GameManager.Instance().RemoveSprite(alchemist);
        GameManager.Instance().RemoveActor(alchemist);
        alchemists.push(alchemist);
    }


}
