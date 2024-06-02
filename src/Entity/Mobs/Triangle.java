package Entity.Mobs;

import Entity.Player;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Triangle extends Mobs {

    public Triangle(Point pos) {
        this.absPos = pos;
        this.collisionSize = new Point(35, 35);
        this.rewardPoint = 3;
        this.HP = 20 + Player.getInstance().getTotalPoints() / 40;
    }

    @Override
    public void onTick() {
        move();

        onCollideCallback();
    }

    @Override
    public void onPaint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (System.currentTimeMillis() - this.lastHitProcessed < 150) { // 150ms 동안 히트 이펙트 표시
            g2d.setColor(Color.red);
        } else {
            g2d.setColor(Color.orange);
        }

        // 삼각형의 중심 좌표 계산
        int centerX = absPos.x;
        int centerY = absPos.y;

        // 회전 각도 계산
        long tickDifference = System.currentTimeMillis() - createdTick;
        double rotationAngle = Math.toRadians(tickDifference / 50.f % 360);

        // AffineTransform을 사용하여 회전 적용
        AffineTransform old = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.rotate(rotationAngle, centerX, centerY);
        g2d.setTransform(transform);

        // 회전된 삼각형 그리기
        Polygon p = new Polygon();
        p.addPoint(centerX, centerY - 20); // 위쪽 꼭지점
        p.addPoint(centerX - (int) (20 * Math.sin(Math.toRadians(60))), centerY + (int) (20 * Math.cos(Math.toRadians(60)))); // 왼쪽 아래 꼭지점
        p.addPoint(centerX + (int) (20 * Math.sin(Math.toRadians(60))), centerY + (int) (20 * Math.cos(Math.toRadians(60)))); // 오른쪽 아래 꼭지점
        g2d.fillPolygon(p);

        // 원래의 변환 상태로 복원
        g2d.setTransform(old);
    }
}
