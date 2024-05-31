package Entity.Mobs;

import java.awt.*;

public class Circle extends Mobs {
    private long lastDashTick = -1;

    public Circle(Point pos) {
        this.absPos = pos;
        this.collisionSize = new Point(35, 35);
        this.rewardPoint = 15;
    }

    @Override
    public void onTick() {
        long timeDiff = System.currentTimeMillis() - lastDashTick;
        if (timeDiff < 1000) {
            double a = -0.000012;
            speed = (float) (a * Math.pow((timeDiff - 500), 2) + 3.0);
        } else if (timeDiff > 1000 && timeDiff < 1500) {
            speed = 0;
        } else {
            lastDashTick = System.currentTimeMillis();
        }

        move();

        onCollideCallback();
    }

    @Override
    public void onPaint(Graphics g) {
        if (System.currentTimeMillis() - this.lastHitProcessed < 150) //150ms동안 히트이펙트 표시
            g.setColor(Color.red);
        else
            g.setColor(Color.cyan);
        g.fillOval(absPos.x, absPos.y, 35, 35);
    }
}
