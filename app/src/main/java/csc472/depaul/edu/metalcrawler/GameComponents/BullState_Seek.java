package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.Random;

public class BullState_Seek extends BullState {
    @Override
    public BullState GetNextState(Bull bull) {
        BullState raycastState = Raycast(bull);

        if (raycastState != null)
            return raycastState;

        Move(bull);

        return this;
    }

    BullState Raycast(Bull bull)
    {
        Player player = GameManager.Instance().GetPlayer();

        if (player.GetY() == bull.y) {
            boolean raycast = true;
            for (int i = bull.x; i != player.x; i += (player.x - bull.x > 0 ? 1 : -1)) {
                if (GameManager.Instance().GetCurrentEnvironment().GetTile(i, bull.y) == null) {
                    raycast = false;
                    break;
                }

                if (i > 1000 || i < -1000) {
                    raycast = false;
                    break;
                }
            }
            if (raycast) {
                if (bull.x < player.x) {
                    bull.MoveRight();
                    return BullFSM.chargeRight;
                } else {
                    bull.MoveLeft();
                    return BullFSM.chargeLeft;
                }
            }
        }
        else if (player.GetX() == bull.x)
        {
            boolean raycast = true;
            for(int i=bull.y; i != player.y; i += (player.y - bull.y > 0 ? 1 : -1))
            {
                if (GameManager.Instance().GetCurrentEnvironment().GetTile(bull.x,i) == null)
                {
                    raycast = false;
                    break;
                }

                if (i > 1000 || i < -1000) {
                    raycast = false;
                    break;
                }
            }
            if (raycast)
            {
                if (bull.y < player.y)
                {
                    bull.MoveDown();
                    return BullFSM.chargeDown;
                }
                else
                {
                    bull.MoveUp();
                    return BullFSM.chargeUp;
                }
            }
        }
        return null;
    }

    void Move(Bull bull)
    {

        Player player = GameManager.Instance().GetPlayer();
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        // 50% chance of aligning X first
        if (random.nextFloat() > 0.5f)
        {
            if (player.GetX() > bull.x)
                bull.MoveRight();
            else if (player.GetX() < bull.x)
                bull.MoveLeft();
            else
            {

                if (player.GetY() < bull.y)
                    bull.MoveUp();
                else if (player.GetY() > bull.y)
                    bull.MoveDown();
                else
                {

                }
            }
        }
        else // Otherwise align y first
        {
            if (player.GetY() < bull.y)
                bull.MoveUp();
            else if (player.GetY() > bull.y)
                bull.MoveDown();
            else
            {
                if (player.GetX() > bull.x)
                    bull.MoveRight();
                else
                    bull.MoveLeft();
            }
        }

    }
}
