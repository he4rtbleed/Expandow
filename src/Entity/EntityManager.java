package Entity;

import Area.MainArea;
import Entity.Mobs.Mobs;
import Utils.Enums.Edge;
import Utils.IntersectHelpers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private static List<Entity> entityList = new ArrayList<Entity>();
    private static List<Entity> toAdd = new ArrayList<Entity>();
    private static List<Entity> toRemove = new ArrayList<Entity>();

    public static List<Entity> getEntityList() {
        return entityList;
    }
    public static List<Entity> getProjectilesList() {
        List<Entity> projectilesList = new ArrayList<Entity>();
        for (Entity entity : entityList) {
            if (entity instanceof Projectiles)
                projectilesList.add(entity);
        }
        return projectilesList;
    }

    public static List<Entity> getMobsList() {
        List<Entity> mobsList = new ArrayList<Entity>();
        for (Entity entity : entityList) {
            if (entity instanceof Mobs)
                mobsList.add(entity);
        }
        return mobsList;
    }

    public static void addEntity(Entity entity) {
        toAdd.add(entity);
    }

    public static void removeEntity(Entity entity) {
        toRemove.add(entity);
    }

    public static void onTick() {
        // 엔티티리스트에 등록된 엔티티들의 onTick 콜백 호출.
        for (Entity entity : entityList) {
            entity.onTick();

            //MainArea 확장 코드
            if (!IntersectHelpers.isPointInRectangle(MainArea.getInstance().getAbsBounds(), entity.getAbsolutePosition())) {
                if (entity.getClass() == PlayerMissile.class) {

                    Edge hittedEdge = IntersectHelpers.getEdge(MainArea.getInstance().getAbsBounds(), entity.getAbsolutePosition());
                    if (hittedEdge != Edge.DEFAULT)
                        MainArea.getInstance().addExpandAmount(hittedEdge, Player.getInstance().getExpandForce());

                    removeEntity(entity);
                    continue;
                }
            }

            //엔티티 청소기
            if (!IntersectHelpers.isPointInRectangle(new Rectangle(0, 0, 10000, 10000), entity.getAbsolutePosition())) {
                removeEntity(entity); //영역밖의 엔티티 제거
                continue;
            }

            // MainArea의 bounds 밖으로 나가지 못하게 하는 로직 추가
            if (entity instanceof Mobs || entity instanceof Player) {
                ensureEntityWithinBounds(entity);
            }
        }

        // 엔티티 루프를 끝낸뒤 제거할 엔티티 제거
        entityList.removeAll(toRemove);
        toRemove.clear();

        // 엔티티 루프를 끝낸뒤 제거까지 마친후 추가할 엔티티 추가 (Ex: 플레이어 미사일)
        entityList.addAll(toAdd);
        toAdd.clear();
    }

    private static void ensureEntityWithinBounds(Entity entity) {
        Rectangle bounds = MainArea.getInstance().getAbsBounds();
        Point pos = entity.getAbsolutePosition();

        // 좌표를 bounds 내로 조정
        if (pos.x < bounds.x) {
            pos.x = bounds.x;
        } else if (pos.x > bounds.x + bounds.width - entity.collisionSize.x) {
            pos.x = bounds.x + bounds.width - entity.collisionSize.x;
        }

        if (pos.y < bounds.y) {
            pos.y = bounds.y;
        } else if (pos.y > bounds.y + bounds.height - entity.collisionSize.y) {
            pos.y = bounds.y + bounds.height - entity.collisionSize.y;
        }
    }
}
