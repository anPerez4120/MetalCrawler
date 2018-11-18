package csc472.depaul.edu.metalcrawler.GameComponents;

public class AlchemistState_Fire extends AlchemistState {
    @Override
    public AlchemistState GetNextState(Alchemist alchemist) {
        alchemist.RunFire();
        if (alchemist.IsFiring())
        {
            return AlchemistFSM.seek;
        }
        else
        {
            return this;
        }
    }
}
