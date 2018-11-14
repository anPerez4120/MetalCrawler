package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.Stack;

public class EnemyFactory {
    Stack<Enemy> enemies;

    private static EnemyFactory instance;
    public static EnemyFactory Instance()
    {
        if (instance == null) {
            instance = new EnemyFactory();
            instance.enemies = new Stack<Enemy>();
        }
        return instance;
    }


    public Enemy GetEnemy(int x, int y)
    {
        if (enemies.empty())
        {
            Enemy enemy = new Enemy(GameManager.Instance().GetView(),x,y);
            return enemy;
        }
        else
        {
            Enemy enemy= enemies.pop();
            GameManager.Instance().AddSprite(enemy);
            GameManager.Instance().AddActor(enemy);
            enemy.SetPosition(x,y);
            return enemy;
        }
    }

    public void ReturnEnemy(Enemy enemy)
    {

        GameManager.Instance().RemoveSprite(enemy);
        GameManager.Instance().RemoveActor(enemy);
        enemies.push(enemy);
    }


}
