package Entity.Mobs;

import Entity.Entity;
import Entity.Movable;
import Entity.Player;
import Entity.EntityManager;
import Entity.PlayerMissile;
import Utils.IntersectHelpers;

import java.awt.*;

public abstract class Mobs extends Entity implements Movable {
    public long lastHitProcessed = -1;
    protected int rewardPoint = 10;

    protected Mobs() {
        HP = 30;
    }

    @Override
    public void move() {
        if (System.currentTimeMillis() - lastHitProcessed > 150) {
            Point playerAbsPos = Player.getInstance().getAbsolutePosition();
            Point playerCollisionSize = Player.getInstance().getCollisionSize();
            if (absPos.x > playerAbsPos.x + playerCollisionSize.x / 2) { //플레이어보다 오른쪽인경우
                absPos.x -= (int) speed;
            } else {
                absPos.x += (int) speed;
            }
            if (absPos.y > playerAbsPos.y + playerCollisionSize.y / 2) { //플레이어보다 밑인경우
                absPos.y -= (int) speed;
            } else {
                absPos.y += (int) speed;
            }
        }
    }

    @Override
    public void onCollideCallback() {
        super.onCollideCallback();

        //충돌 검사 로직
        for (Entity projectile : EntityManager.getProjectilesList()) {
            if (projectile.getClass() == PlayerMissile.class) {
                if (IntersectHelpers.isCollide(this, projectile)) {
                    this.HP -= Player.getInstance().getAttackDamage();
                    lastHitProcessed = System.currentTimeMillis();
                    EntityManager.removeEntity(projectile);

                    if (this.HP <= 0) {
                        Player.getInstance().addPoint(this.rewardPoint);
                        EntityManager.removeEntity(this);
                    }
                }
            }
        }
    }
}
