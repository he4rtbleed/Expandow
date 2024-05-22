package Entity;

import Area.MainArea;
import Utils.CoordinateConvertHelper;

import java.awt.*;

public class Player extends Entity implements Movable {
    private static Player Instance = null;
    public boolean[] keyMapped = { false, false, false, false }; //방향키 상, 하, 좌, 우

    public Player(Point pos) {
        this.absPos = pos;
        Instance = this;
    }

    public static Player getInstance() {
        return Instance;
    }

    @Override
    public void move() {
        this.absPos.x += this.direction.x;
        this.absPos.y += this.direction.y;
    }

    @Override
    public void onTick() {
        if (keyMapped[0] == keyMapped[1]) //'상' '하' 키 동시에 눌렸거나 안눌린경우 정지
            this.direction.y = 0;
        else this.direction.y = keyMapped[0] ? -1 : 1; //'상' 키가 눌린경우 y = -1

        if (keyMapped[2] == keyMapped[3]) //'좌' '우' 키 동시에 눌렸거나 안눌린경우 정지
            this.direction.x = 0;
        else this.direction.x = keyMapped[2] ? -1 : 1;

        move();
    }

    @Override
    public void onCollideCallback() {

    }

    @Override
    public void onPaint(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(absPos.x, absPos.y, 10, 10);
    }
}
