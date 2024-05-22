package Entity;

import Area.MainArea;
import Utils.Enums.Edge;
import Utils.IntersectHelpers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityManager {
    private static List<Entity> entityList = new ArrayList<Entity>();

    public static List<Entity> getEntityList() {
        return entityList;
    }

    private static MainArea mainArea;

    public static void setMainArea(MainArea mainArea) {
        EntityManager.mainArea = mainArea;
    }

    public static void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public static void removeEntity(Entity entity) {
        entityList.remove(entity);
    }

    public static void onTick() {
        // 엔티티리스트에 등록된 엔티티들의 onTick 콜백 호출.
        Iterator<Entity> iterator = entityList.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            entity.onTick();

            //MainArea 확장 코드
            if (!IntersectHelpers.isPointInRectangle(mainArea.getAbsBounds(), entity.getAbsolutePosition())) {
                if (entity.getClass() == PlayerMissile.class) {

                    Edge hittedEdge = IntersectHelpers.getEdge(mainArea.getAbsBounds(), entity.getAbsolutePosition());
                    if (hittedEdge != Edge.DEFAULT)
                        mainArea.addExpandAmount(hittedEdge, 15);

                    iterator.remove();
                    continue;
                }
            }

            //엔티티 청소기
            if (!IntersectHelpers.isPointInRectangle(new Rectangle(0, 0, 10000, 10000), entity.getAbsolutePosition())) {
                iterator.remove(); //영역밖의 엔티티 제거
                continue;
            }
        }
    }
}
