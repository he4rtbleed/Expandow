package Entity;

import Utils.CoordinateConvertHelper;

import java.awt.*;
import java.awt.event.*;

public class PlayerController extends KeyAdapter implements MouseListener {
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
        PlayerMissile missile = new PlayerMissile(targetEntity,
                CoordinateConvertHelper.GetAngleFromTwoPoints(targetEntity.absPos, e.getLocationOnScreen()));
        EntityManager.addEntity(missile);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        PlayerMissile missile = new PlayerMissile(targetEntity,
                CoordinateConvertHelper.GetAngleFromTwoPoints(targetEntity.absPos, e.getLocationOnScreen()));
        EntityManager.addEntity(missile);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
