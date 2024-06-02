package Entity;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class PlayerMissile extends Projectiles {
    double angle;
    double speed = 10.f;

    public PlayerMissile(Entity owner, Point startAbsPos, double angle) {
        super(owner);
        this.startAbsPos = startAbsPos;
        this.absPos = (Point) this.startAbsPos.clone();
        this.collisionSize = new Point(15, 5);
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);

        // 타원의 중심 좌표 계산
        int centerX = absPos.x + collisionSize.x / 2;
        int centerY = absPos.y + collisionSize.y / 2;

        // AffineTransform을 사용하여 회전 적용
        AffineTransform old = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, centerX, centerY); // 회전 변환 설정

        g2d.setTransform(transform);

        // 타원 그리기
        g2d.fillOval(absPos.x, absPos.y, collisionSize.x, collisionSize.y);

        // 원래의 변환 상태로 복원
        g2d.setTransform(old);
    }
}
