package Utils;

import java.awt.*;

public class CoordinateConvertHelper {
    public static Point AbsolutePositionToAreaPosition(Point absPos, Rectangle absBounds) {
        int relativeX = absPos.x - absBounds.x;
        int relativeY = absPos.y - absBounds.y;
        return new Point(relativeX, relativeY);
    }

    public static double GetAngleFromTwoPoints(Point pt1, Point pt2) {
        return Math.atan2(pt2.y - pt1.y, pt2.x - pt1.x);
    }
}
