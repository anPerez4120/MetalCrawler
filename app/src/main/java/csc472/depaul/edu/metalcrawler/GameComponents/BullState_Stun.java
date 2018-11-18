package csc472.depaul.edu.metalcrawler.GameComponents;

public class BullState_Stun extends BullState {
    @Override
    public BullState GetNextState(Bull bull)
    {
        bull.RunStun();
        if (bull.IsStunned())
        {
            return BullFSM.seek;
        }
        else
        {
            return this;
        }
    }

}
