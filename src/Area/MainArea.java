package Area;

import Entity.Entity;
import Entity.EntityManager;
import Utils.Enums.Edge;
import Utils.IntersectHelpers;

import java.awt.*;

public class MainArea extends Area {

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

        //확장 로직
        if (accumulatedExpandAmount[Edge.BOTTOM.ordinal()] != 0) {
            setBounds(absBounds.x, absBounds.y, absBounds.width, absBounds.height + accumulatedExpandAmount[Edge.BOTTOM.ordinal()]);
            accumulatedExpandAmount[Edge.BOTTOM.ordinal()] = accumulatedExpandAmount[Edge.BOTTOM.ordinal()] < 0 ?
                    Math.min(0, accumulatedExpandAmount[Edge.BOTTOM.ordinal()] + 10) :
                    Math.max(0, accumulatedExpandAmount[Edge.BOTTOM.ordinal()] - 10);
        }
        if (accumulatedExpandAmount[Edge.TOP.ordinal()] != 0) {
            setBounds(absBounds.x, absBounds.y - accumulatedExpandAmount[Edge.TOP.ordinal()], absBounds.width, absBounds.height + accumulatedExpandAmount[Edge.TOP.ordinal()]);
            accumulatedExpandAmount[Edge.TOP.ordinal()] = accumulatedExpandAmount[Edge.TOP.ordinal()] < 0 ?
                    Math.min(0, accumulatedExpandAmount[Edge.TOP.ordinal()] + 10) :
                    Math.max(0, accumulatedExpandAmount[Edge.TOP.ordinal()] - 10);
        }
        if (accumulatedExpandAmount[Edge.LEFT.ordinal()] != 0) {
            setBounds(absBounds.x - accumulatedExpandAmount[Edge.LEFT.ordinal()], absBounds.y, absBounds.width + accumulatedExpandAmount[Edge.LEFT.ordinal()], absBounds.height);
            accumulatedExpandAmount[Edge.LEFT.ordinal()] = accumulatedExpandAmount[Edge.BOTTOM.ordinal()] < 0 ?
                    Math.min(0, accumulatedExpandAmount[Edge.LEFT.ordinal()] + 10) :
                    Math.max(0, accumulatedExpandAmount[Edge.LEFT.ordinal()] - 10);
        }
        if (accumulatedExpandAmount[Edge.RIGHT.ordinal()] != 0) {
            setBounds(absBounds.x, absBounds.y, absBounds.width + accumulatedExpandAmount[Edge.RIGHT.ordinal()], absBounds.height);
            accumulatedExpandAmount[Edge.RIGHT.ordinal()] = accumulatedExpandAmount[Edge.RIGHT.ordinal()] < 0 ?
                    Math.min(0, accumulatedExpandAmount[Edge.RIGHT.ordinal()] + 10) :
                    Math.max(0, accumulatedExpandAmount[Edge.RIGHT.ordinal()] - 10);
        }

        //
        _g.drawImage(img, -absBounds.getLocation().x, -absBounds.getLocation().y, null);

        repaint();
    }

    public void areaReducer() {
        for (int i = 0; i < 4; i++) {
            accumulatedExpandAmount[i] -= 1;
        }
    }
}