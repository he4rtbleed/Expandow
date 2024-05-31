package Entity.Mobs;

import java.awt.*;

public class Triangle extends Mobs {

    public Triangle(Point pos) {
        this.absPos = pos;
        this.collisionSize = new Point(35, 35);
        this.rewardPoint = 10;
    }

    @Override
    public void onTick() {
        move();

        onCollideCallback();
    }

    @Override
    public void onPaint(Graphics g) {
        if (System.currentTimeMillis() - this.lastHitProcessed < 150) //150ms동안 히트이펙트 표시
            g.setColor(Color.red);
        else
            g.setColor(Color.orange);
        Polygon p = new Polygon();
        p.addPoint(absPos.x, absPos.y - 20);
        p.addPoint(absPos.x - 20, absPos.y + 20);
        p.addPoint(absPos.x + 20, absPos.y + 20);
        g.fillPolygon(p);
    }
}
