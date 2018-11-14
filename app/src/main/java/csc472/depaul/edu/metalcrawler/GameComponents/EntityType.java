package csc472.depaul.edu.metalcrawler.GameComponents;

public enum EntityType {
    NONE (0x0),
    PLAYER (0x1),
    ENEMY (0x2),
    DOOR (0x4),
    GOLD (0x5);

    final int i;
    EntityType(int i) {
        this.i = i;
    }
}
