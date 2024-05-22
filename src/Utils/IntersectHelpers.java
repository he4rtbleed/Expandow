package Utils;

import Utils.Enums.Edge;

import java.awt.*;

public class IntersectHelpers {
    public static boolean isPointInRectangle(Rectangle rectangle, Point point) {
        return point.x >= rectangle.x && point.y >= rectangle.y
            && point.x <= rectangle.x + rectangle.width
            && point.y <= rectangle.y + rectangle.height;
    }

    public static Edge getEdge(Rectangle rectangle, Point hittedPosition) {
        if (hittedPosition.x < rectangle.x) return Edge.LEFT;
        else if (hittedPosition.x > rectangle.x + rectangle.width) return Edge.RIGHT;

        if (hittedPosition.y < rectangle.y) return Edge.TOP;
        else if (hittedPosition.y > rectangle.y + rectangle.height) return Edge.BOTTOM;
        return Edge.DEFAULT;
    }
}