package Entity;

import org.w3c.dom.css.Rect;

import java.awt.*;

public abstract class Entity {
    protected boolean isValid = true;
    protected int HP = 100;
    protected Point absPos; //모니터에서의 엔티티의 절대좌표
    protected Point collisionSize; //충돌 체크를 위한 size
    protected float speed = 1.0f; //엔티티의 움직임 속도
    public Point direction = new Point();

    public Point getAbsolutePosition() {
        return this.absPos;
    }
    public Point getCollisionSize() {
        return this.collisionSize;
    }
    public Rectangle getAbsBounds() {
        return new Rectangle(absPos.x - collisionSize.x / 2, absPos.y - collisionSize.y / 2,
                                collisionSize.x, collisionSize.y);
    }

    public int getHP() {
        return HP;
    }

    public void reduceHP(int amount) {
        HP -= amount;
    }

    public void onCollideCallback() {

    }

    public abstract void onTick();

    public abstract void onPaint(Graphics g);
}
