package Entity;

import java.awt.*;

public abstract class Projectiles extends Entity implements Movable {
    Entity owner;
    Point startAbsPos;
    double currentMoveFactor = 1.0; //현재 얼마나 이동했는지 tick당 1.0씩 증가

    public Projectiles(Entity owner) {
        this.owner = owner;
    }
}
