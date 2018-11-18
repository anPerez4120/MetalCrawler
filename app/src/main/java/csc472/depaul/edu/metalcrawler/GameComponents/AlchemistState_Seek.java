package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.Random;

public class AlchemistState_Seek extends AlchemistState {
    @Override
    public AlchemistState GetNextState(Alchemist alchemist) {
        AlchemistState raycastState = Raycast(alchemist);

        if (raycastState != null) {
            return raycastState;
        }
        Move(alchemist);

        return this;
    }

    AlchemistState Raycast(Alchemist alchemist) {
        Player player = GameManager.Instance().GetPlayer();

        if (player.GetY() == alchemist.y) {
            boolean raycast = true;
            for (int i = alchemist.x; i != player.x; i += (player.x - alchemist.x > 0 ? 1 : -1)) {
                if (GameManager.Instance().GetCurrentEnvironment().GetTile(i, alchemist.y) == null) {
                    raycast = false;
                    break;
                }

                if (i > 1000 || i < -1000) {
                    raycast = false;
                    break;
                }
            }
            if (raycast) {
                if (alchemist.x < player.x) {
                    SpawnFireball(alchemist,1,0);
                    return AlchemistFSM.fire;
                } else {
                    SpawnFireball(alchemist,-1,0);
                    return AlchemistFSM.fire;
                }
            }
        } else if (player.GetX() == alchemist.x) {
            boolean raycast = true;
            for (int i = alchemist.y; i != player.y; i += (player.y - alchemist.y > 0 ? 1 : -1)) {
                if (GameManager.Instance().GetCurrentEnvironment().GetTile(alchemist.x, i) == null) {
                    raycast = false;
                    break;
                }

                if (i > 1000 || i < -1000) {
                    raycast = false;
                    break;
                }
            }
            if (raycast) {
                if (alchemist.y < player.y) {
                    SpawnFireball(alchemist,0,1);
                    return AlchemistFSM.fire;
                } else {
                    SpawnFireball(alchemist,0,-1);
                    return AlchemistFSM.fire;
                }
            }
        }
        return null;
    }

    void Move(Alchemist alchemist) {

        Player player = GameManager.Instance().GetPlayer();
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        // 50% chance of aligning X first
        if (random.nextFloat() > 0.5f) {
            if (player.GetX() > alchemist.x)
                alchemist.MoveRight();
            else if (player.GetX() < alchemist.x)
                alchemist.MoveLeft();
            else {

                if (player.GetY() < alchemist.y)
                    alchemist.MoveUp();
                else if (player.GetY() > alchemist.y)
                    alchemist.MoveDown();
                else {

                }
            }
        } else // Otherwise align y first
        {
            if (player.GetY() < alchemist.y)
                alchemist.MoveUp();
            else if (player.GetY() > alchemist.y)
                alchemist.MoveDown();
            else {
                if (player.GetX() > alchemist.x)
                    alchemist.MoveRight();
                else
                    alchemist.MoveLeft();
            }
        }
    }

    void SpawnFireball(Alchemist alchemist, int dx, int dy)
    {
        Fireball fireball = EnemyFactory.Instance().GetFireball(alchemist.x + dx,alchemist.y + dy);
        alchemist.SetFire(4);

        fireball.SetDX(dx);
        fireball.SetDY(dy);
    }
}
