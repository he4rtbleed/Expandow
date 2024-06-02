package Area;

import Entity.Entity;
import Entity.EntityManager;
import Entity.Player;
import Utils.Enums.Edge;
import Utils.IntersectHelpers;

import java.awt.*;

import static Utils.GameConditions.isGamePaused;

public class MainArea extends Area {
    private static MainArea instance;

    public static MainArea getInstance() {
        return instance;
    }

    public static void setInstance(MainArea instance) {
        MainArea.instance = instance;
    }

    boolean isInitialized = false;

    int[] accumulatedExpandAmount = { 0, 0, 0, 0, 0 };
    int[] targetExpandAmount = {0, 0, 0, 0, 0 };

    long lastAreaReducedTick = -1;

    public void addExpandAmount(Edge edge, int amount) {
        targetExpandAmount[edge.ordinal()] += amount;
    }

    public MainArea() {
        setTitle("MainArea");
        setSize(new Dimension(500, 500));
        setLocationRelativeTo(null);

        setVisible(true);

        requestFocus();
        setAutoRequestFocus(true);

        updateAbsolutePositionOfArea();
        updateWindowSize();

        isInitialized = true;
    }

    public int getAreaOfSquare() {
        Dimension pt = getSize();
        return pt.width * pt.height;
    }

    @Override
    public void paint(Graphics _g) {
        //JFrame 을 상속받은 Area클래스를 상속받았으므로 paint 메소드 오버라이딩 가능
        updateAbsolutePositionOfArea(); //스크린상의 절대좌표 업데이트
        updateWindowSize(); //창의 크기 업데이트

        //모니터의 크기 가져옴
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //모니터의 크기만큼의 비트맵 생성
        Image img = createImage(screenSize.width, screenSize.height);
        Graphics g = img.getGraphics();
        g.clearRect(0, 0, screenSize.width, screenSize.height);
        //모니터의 크기만큼 비트맵을 지움

        //영역 안의 엔티티를 방금 만든 비트맵에 그려줌
        for (Entity entity : EntityManager.getEntityList()) {
            boolean inArea = IntersectHelpers.isPointInRectangle(this.absBounds, entity.getAbsolutePosition());
            if (inArea) {
                entity.onPaint(g);
            }
        }

        //플레이어 정보 그려줌
        if (Player.getInstance() != null) {
            g.setColor(Color.BLACK);
            g.drawString("POINT: " + Player.getInstance().getPoint(), absBounds.x + 10, absBounds.y + 50);
            g.drawString("HP: " + Player.getInstance().getHP() + "/" + Player.getInstance().getMaxHp(), absBounds.x + absBounds.width - 100, absBounds.y + 50);
        }

        // _ 가붙은 g객체가 swing 프레임의 그래픽 객체이고 img(g) 는 위에서 따로 만든 비트맵
        // img 비트맵은 모니터 크기와 같음
        // 최종적으로 모든 엔티티와 정보를 그려준 비트맵을 swing 프레임에 그려줌
        _g.drawImage(img, -absBounds.getLocation().x, -absBounds.getLocation().y, null);

        repaint();
    }

    public void validateArea() {
        if (isGamePaused)
            return;

        if (System.currentTimeMillis() - lastAreaReducedTick > 100 - getAreaOfSquare() / 10000) {
            lastAreaReducedTick = System.currentTimeMillis();
            for (int i = 0; i < 4; i++) {
                targetExpandAmount[i] -= 1;
            }
        }

        // 확장/축소 로직
        boolean boundsChanged = false;

        for (Edge edge : Edge.values()) {
            int index = edge.ordinal();
            if (accumulatedExpandAmount[index] != targetExpandAmount[index]) {
                int changeAmount = (int) ((targetExpandAmount[index] - accumulatedExpandAmount[index]) * 0.2);
                accumulatedExpandAmount[index] += changeAmount;
                if (edge == Edge.BOTTOM) {
                    absBounds.height += changeAmount;
                } else if (edge == Edge.TOP) {
                    absBounds.y -= changeAmount;
                    absBounds.height += changeAmount;
                } else if (edge == Edge.LEFT) {
                    absBounds.x -= changeAmount;
                    absBounds.width += changeAmount;
                } else if (edge == Edge.RIGHT) {
                    absBounds.width += changeAmount;
                }
                boundsChanged = true;
            }
        }

        // 최소 크기 유지
        if (absBounds.width < 300) {
            absBounds.width = 300;
        }
        if (absBounds.height < 200) {
            absBounds.height = 200;
        }

        if (boundsChanged) {
            setBounds(absBounds.x, absBounds.y, absBounds.width, absBounds.height);
        }
    }
}