package Entity.Mobs;

import Area.MainArea;
import Entity.EntityManager;
import Entity.Player;
import Entity.PlayerMissile;
import Utils.CoordinateConvertHelper;
import Utils.Enums.Edge;

import java.awt.*;
import java.util.Random;

public class Octagon extends Mobs {
    private long nextMissileFire = -1; //몹의 미사일 공격속도를 위해 정의
    private static Random random = new Random();

    public Octagon(Point pos) {
        this.absPos = pos;
        this.collisionSize = new Point(40, 40);
        this.rewardPoint = 20;
        this.HP = 60;
        this.speed = 0;
    }

    private void checkAndFireMissile() {
        if (System.currentTimeMillis() > nextMissileFire) {
            nextMissileFire = System.currentTimeMillis() + random.nextInt(2000, 4000);

            OctagonMissile missile = new OctagonMissile(this,
                    CoordinateConvertHelper.GetAngleFromTwoPoints(this.absPos, Player.getInstance().getAbsolutePosition()));
            EntityManager.addEntity(missile);
        }
    }

    @Override
    public void onTick() {
        move();

        checkAndFireMissile();

        onCollideCallback();
    }

    @Override
    public void onPaint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (System.currentTimeMillis() - this.lastHitProcessed < 150) { // 150ms 동안 히트 이펙트 표시
            g2d.setColor(Color.red);
        } else {
            g2d.setColor(Color.magenta);
        }

        // 팔각형의 중심 좌표 계산
        int centerX = absPos.x + collisionSize.x / 2;
        int centerY = absPos.y + collisionSize.y / 2;

        // 팔각형 그리기
        Polygon p = new Polygon();
        int r = 20; // 반지름
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(45 * i);
            int x = centerX + (int) (r * Math.cos(angle));
            int y = centerY + (int) (r * Math.sin(angle));
            p.addPoint(x, y);
        }
        g2d.fillPolygon(p);
    }
}
