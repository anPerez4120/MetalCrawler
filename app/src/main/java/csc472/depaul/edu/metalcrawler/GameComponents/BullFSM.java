package csc472.depaul.edu.metalcrawler.GameComponents;

public class BullFSM {
    public static final BullState_Seek seek = new BullState_Seek();
    public static final BullState_ChargeLeft chargeLeft = new BullState_ChargeLeft();
    public static final BullState_ChargeRight chargeRight = new BullState_ChargeRight();
    public static final BullState_ChargeUp chargeUp = new BullState_ChargeUp();
    public static final BullState_ChargeDown chargeDown = new BullState_ChargeDown();
    public static final BullState_Stun stun = new BullState_Stun();
}
