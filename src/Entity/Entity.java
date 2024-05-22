package Entity;

import java.awt.*;

public abstract class Entity {
    int HP;
    Point absPos; //모니터에서의 엔티티의 절대좌표
    Point collisionSize; //충돌 체크를 위한 size
    float speed = 1.0f; //엔티티의 움직임 속도
    public Point direction = new Point();

    public Point getAbsolutePosition() {
        return this.absPos;
    }

    public abstract void onTick();

    public abstract void onCollideCallback();

    public abstract void onPaint(Graphics g);
}
