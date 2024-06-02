package Entity;

import Area.ShopArea;
import Utils.CoordinateConvertHelper;

import java.awt.*;
import java.awt.event.*;

public class PlayerController extends KeyAdapter implements MouseListener, MouseMotionListener {
    Player targetEntity;

    public PlayerController(Player entity) {
        this.targetEntity = entity;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.targetEntity.keyMapped[0] = true;
            case KeyEvent.VK_S -> this.targetEntity.keyMapped[1] = true;
            case KeyEvent.VK_A -> this.targetEntity.keyMapped[2] = true;
            case KeyEvent.VK_D -> this.targetEntity.keyMapped[3] = true;
            case KeyEvent.VK_SPACE -> ShopArea.getInstance().showShop();
            default -> { }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> this.targetEntity.keyMapped[0] = false;
            case KeyEvent.VK_S -> this.targetEntity.keyMapped[1] = false;
            case KeyEvent.VK_A -> this.targetEntity.keyMapped[2] = false;
            case KeyEvent.VK_D -> this.targetEntity.keyMapped[3] = false;
            default -> { }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        targetEntity.mousePos = e.getLocationOnScreen(); //눌린경우 마우스 위치 업데이트
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        targetEntity.mousePos = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE); //떼진경우 마우스 위치 MAX, MAX 로 변경
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        targetEntity.mousePos = e.getLocationOnScreen(); //눌린경우 마우스 위치 업데이트
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
