package Area;

import javax.swing.*;
import java.awt.*;

public abstract class Area extends JFrame {

    Dimension windowSize; //해당 Area의 크기
    Rectangle absBounds; //모니터에서 해당 Area의 절대좌표

    protected void updateWindowSize() {
        this.windowSize = absBounds.getSize();
    }

    protected void updateAbsolutePositionOfArea() {
        this.absBounds = this.getBounds();
    };

    public Dimension getWindowSize() {
        return windowSize;
    }

    public Rectangle getAbsBounds() {
        return absBounds;
    }
}
