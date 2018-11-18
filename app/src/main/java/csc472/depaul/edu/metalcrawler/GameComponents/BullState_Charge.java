package csc472.depaul.edu.metalcrawler.GameComponents;

public class BullState_Charge extends BullState{
    int dx;
    int dy;

    @Override
    public BullState GetNextState(Bull bull) {
        int sx = bull.x;
        int sy = bull.y;

        switch(dx)
        {
            case 1:
                bull.MoveRight();
                break;
            case -1:
                bull.MoveLeft();
                break;
            case 0:
                switch(dy)
                {
                    case 1:
                        bull.MoveDown();
                        break;
                    case -1:
                        bull.MoveUp();
                        break;
                }
        }

        if (sx == bull.x && sy == bull.y)
        {
            bull.SetStun(3);
            return BullFSM.stun;
        }
        else
        {
            return this;
        }
    }
}
