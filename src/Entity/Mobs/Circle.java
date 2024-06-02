package Entity.Mobs;

import Entity.Player;

import java.awt.*;

public class Circle extends Mobs {
    private long lastDashTick = -1;

    public Circle(Point pos) {
        this.absPos = pos;
        this.collisionSize = new Point(20, 20);
        this.rewardPoint = 6;
        this.HP = 10 + Player.getInstance().getTotalPoints() / 40; //100마리정도 잡으면 평균 400점이므로 + 10이 적당하므로 40으로 나누어준다
    }

    @Override
    public void onTick() {
        long timeDiff = System.currentTimeMillis() - lastDashTick;
        if (timeDiff < 1000) {
            double a = -0.000016;
            speed = (float) (a * Math.pow((timeDiff - 500), 2) + 4.0);
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
        g.fillOval(absPos.x, absPos.y, 20, 20);
    }
}
