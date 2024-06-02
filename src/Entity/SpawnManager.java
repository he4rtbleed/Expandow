package Entity;

import Area.MainArea;
import Entity.Mobs.Circle;
import Entity.Mobs.Octagon;
import Entity.Mobs.Triangle;
import Utils.Enums.Edge;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.Random;

import static Utils.GameConditions.isGamePaused;

public class SpawnManager {
    private static long nextSpawnTick = -1;
    private static final Random random = new Random();

    private static Point getRandomSpawnPos() { //현재 영역의 모서리에서 랜덤한 스폰위치를 가져오기위한 메서드
        Rectangle absBounds = MainArea.getInstance().getAbsBounds();
        switch (random.nextInt(0, 4)) {
            case 0: //TOP
                return new Point(random.nextInt(absBounds.x, absBounds.x + absBounds.width), absBounds.y);
            case 1: //BOTTOM
                return new Point(random.nextInt(absBounds.x, absBounds.x + absBounds.width), absBounds.y + absBounds.height);
            case 2: //LEFT
                return new Point(absBounds.x, random.nextInt(absBounds.y, absBounds.y + absBounds.height));
            case 3: //RIGHT
                return new Point(absBounds.x + absBounds.width, random.nextInt(absBounds.y, absBounds.y + absBounds.height));
        }
        return new Point(0, 0); //이건 호출되면 절대! 안됨.
    }

    public static void onTick() {
        if (isGamePaused)
            return;

        //만약 최근 몹스폰시간과 현재 시간의 시간차가 다음 스폰시간을 지난경우
        //Ex) 현재시각 5300ms - 최근 몹스폰시각 4900ms = 400ms > 300ms (최근 몹 스폰시각으로부터 300ms 가 지났으므로 스폰)
        if (System.currentTimeMillis() > nextSpawnTick) {
            //다음 몹 스폰시각을 0.5~2초 이후로 설정 //난이도 조절을위해 2-(총얻은점수) 초로 조정
            nextSpawnTick = System.currentTimeMillis() + random.nextInt(500, Math.min(1500, 2000 - Player.getInstance().getTotalPoints()));

            switch (random.nextInt(0, 4)) {
                case 0 -> EntityManager.addEntity(new Circle(getRandomSpawnPos()));
                case 1 -> EntityManager.addEntity(new Triangle(getRandomSpawnPos()));
                case 2 -> EntityManager.addEntity(new Octagon(getRandomSpawnPos()));
                default -> {
                }
            }
        }
    }
}
