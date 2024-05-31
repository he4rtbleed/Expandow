package Entity.Mobs;

import java.awt.*;
import java.util.Random;

public class Octagon extends Mobs {
    private long nextMissileFire = -1; //몹의 미사일 공격속도를 위해 정의
    private static Random random = new Random();

    public Octagon(Point pos) {
        this.absPos = pos;
        this.collisionSize = new Point(35, 35);
        this.rewardPoint = 20;
    }

    private void checkAndFireMissile() {
        if (System.currentTimeMillis() > nextMissileFire) {
            nextMissileFire = System.currentTimeMillis() + random.nextInt(1000, 3000);

        }
    }

    @Override
    public void onTick() {
        move();

        onCollideCallback();
    }

    @Override
    public void onPaint(Graphics g) {
        g.setColor(Color.MAGENTA);
        Polygon p = new Polygon();
        p.addPoint(absPos.x, absPos.y - 20);
        p.addPoint(absPos.x - 20, absPos.y + 20);
        p.addPoint(absPos.x + 20, absPos.y + 20);
        g.fillPolygon(p);
    }
}
