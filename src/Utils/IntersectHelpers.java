package Utils;

import Utils.Enums.Edge;

import java.awt.*;

import Entity.Entity;
import org.w3c.dom.css.Rect;

public class IntersectHelpers {
    public static boolean isPointInRectangle(Rectangle rectangle, Point point) {
        return point.x >= rectangle.x && point.y >= rectangle.y
            && point.x <= rectangle.x + rectangle.width
            && point.y <= rectangle.y + rectangle.height;
    }

    public static boolean isRectIntersects(Rectangle rectangleA, Rectangle rectangleB) {
        return rectangleA.intersects(rectangleB);
    }

    public static boolean isCollide(Entity a, Entity b) {
        Rectangle rectA = new Rectangle(a.getAbsolutePosition().x, a.getAbsolutePosition().y, a.getCollisionSize().x, a.getCollisionSize().y);
        Rectangle rectB = new Rectangle(b.getAbsolutePosition().x, b.getAbsolutePosition().y, b.getCollisionSize().x, b.getCollisionSize().y);
        return rectA.intersects(rectB);
    }

    public static Edge getEdge(Rectangle rectangle, Point hittedPosition) {
        if (hittedPosition.x < rectangle.x) return Edge.LEFT;
        else if (hittedPosition.x > rectangle.x + rectangle.width) return Edge.RIGHT;

        if (hittedPosition.y < rectangle.y) return Edge.TOP;
        else if (hittedPosition.y > rectangle.y + rectangle.height) return Edge.BOTTOM;
        return Edge.DEFAULT;
    }
}