package Area;

import Entity.Entity;
import Entity.EntityManager;
import Entity.Player;
import Utils.Enums.Edge;
import Utils.IntersectHelpers;

import java.awt.*;

public class MainArea extends Area {
    private static MainArea instance;

    public static MainArea getInstance() {
        return instance;
    }

    public static void setInstance(MainArea instance) {
        MainArea.instance = instance;
    }

    boolean isInitialized = false;

    int[] accumulatedExpandAmount = { 0, 0, 0, 0 };

    public void addExpandAmount(Edge edge, int amount) {
        accumulatedExpandAmount[edge.ordinal()] += amount;
    }

    public MainArea() {
        setTitle("MainArea");
        setSize(new Dimension(500, 500));
        setLocationRelativeTo(null);

        setVisible(true);

        requestFocus();
        setAutoRequestFocus(true);

        isInitialized = true;
    }

    @Override
    public void paint(Graphics _g) {
        //JFrame 을 상속받은 Area클래스를 상속받았으므로 paint 메소드 오버라이딩 가능
        updateAbsolutePositionOfArea();
        updateWindowSize();

        //모니터의 크기 가져옴
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //모니터의 크기만큼의 비트맵 생성
        Image img = createImage(screenSize.width, screenSize.height);
        Graphics g = img.getGraphics();
        g.clearRect(0, 0, screenSize.width, screenSize.height);

        //영역 안의 엔티티 페인팅
        for (Entity entity : EntityManager.getEntityList()) {
            boolean inArea = IntersectHelpers.isPointInRectangle(this.absBounds, entity.getAbsolutePosition());
            if (inArea) {
                entity.onPaint(g);
            }
        }

        // 확장/축소 로직
        boolean boundsChanged = false;

        if (accumulatedExpandAmount[Edge.BOTTOM.ordinal()] != 0) {
            absBounds.height += accumulatedExpandAmount[Edge.BOTTOM.ordinal()];
            accumulatedExpandAmount[Edge.BOTTOM.ordinal()] = adjustAmount(accumulatedExpandAmount[Edge.BOTTOM.ordinal()]);
            boundsChanged = true;
        }
        if (accumulatedExpandAmount[Edge.TOP.ordinal()] != 0) {
            absBounds.y -= accumulatedExpandAmount[Edge.TOP.ordinal()];
            absBounds.height += accumulatedExpandAmount[Edge.TOP.ordinal()];
            accumulatedExpandAmount[Edge.TOP.ordinal()] = adjustAmount(accumulatedExpandAmount[Edge.TOP.ordinal()]);
            boundsChanged = true;
        }
        if (accumulatedExpandAmount[Edge.LEFT.ordinal()] != 0) {
            absBounds.x -= accumulatedExpandAmount[Edge.LEFT.ordinal()];
            absBounds.width += accumulatedExpandAmount[Edge.LEFT.ordinal()];
            accumulatedExpandAmount[Edge.LEFT.ordinal()] = adjustAmount(accumulatedExpandAmount[Edge.LEFT.ordinal()]);
            boundsChanged = true;
        }
        if (accumulatedExpandAmount[Edge.RIGHT.ordinal()] != 0) {
            absBounds.width += accumulatedExpandAmount[Edge.RIGHT.ordinal()];
            accumulatedExpandAmount[Edge.RIGHT.ordinal()] = adjustAmount(accumulatedExpandAmount[Edge.RIGHT.ordinal()]);
            boundsChanged = true;
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

        if (Player.getInstance() != null) {
            g.setColor(Color.BLACK);
            g.drawString("POINT: " + Player.getInstance().getPoint(), absBounds.x + 10, absBounds.y + 50);
            g.drawString("HP: " + Player.getInstance().getHP() + "/" + Player.getInstance().getMaxHp(), absBounds.x + absBounds.width - 100, absBounds.y + 50);
        }

        // _g 가 swing 프레임의 그래픽 객체이고 img 는 따로 만든 비트맵
        // img 비트맵은 모니터 크기와 같음
        // 즉 swing 프레임에
        _g.drawImage(img, -absBounds.getLocation().x, -absBounds.getLocation().y, null);

        repaint();
    }

    private int adjustAmount(int amount) {
        if (amount < 0) {
            return Math.min(0, amount + 10);
        } else {
            return Math.max(0, amount - 10);
        }
    }

    public void areaReducer() {
        for (int i = 0; i < 4; i++) {
            accumulatedExpandAmount[i] -= 1;
        }
    }
}