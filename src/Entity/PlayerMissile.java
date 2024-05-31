package Entity;

import java.awt.*;

public class PlayerMissile extends Projectiles {
    double angle;
    double speed = 5.f;

    public PlayerMissile(Entity owner, double angle) {
        super(owner);
        this.startAbsPos = (Point) owner.absPos.clone();
        this.absPos = (Point) this.startAbsPos.clone();
        this.collisionSize = new Point(10, 10);
        this.angle = angle;
    }

    @Override
    public void onTick() {
        move();
    }

    @Override
    public void move() {
        //현재 좌표에서 방향벡터로 이동하지 않고 시작 좌표에서 currentMoveFactor 를 활용해 이동하는이유는
        //정확도 때문이다 현재 프로젝트는 실수 좌표계를 사용하지않음에 따른 오차가 있으므로 이와같은 설계를 적용함.
        absPos.x = (int) (startAbsPos.x + speed * currentMoveFactor * Math.cos(angle));
        absPos.y = (int) (startAbsPos.y + speed * currentMoveFactor * Math.sin(angle));
        currentMoveFactor++;
    }

    @Override
    public void onPaint(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(absPos.x, absPos.y, 10, 10);
    }
}
