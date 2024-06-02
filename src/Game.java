import Area.MainArea;
import Entity.PlayerController;
import Entity.EntityManager;
import Entity.Player;
import Entity.SpawnManager;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        //모든 엔티티들이 이동할 수 있는 영역 (JFrame) 객체 생성
        MainArea mainArea = new MainArea();
        MainArea.setInstance(mainArea);

        //플레이어 객체 생성
        new Player(new Point((int)mainArea.getAbsBounds().getCenterX(), (int)mainArea.getAbsBounds().getCenterY()));
        //생성한 플레이어 객체를 움직일 컨트롤러 객체 생성
        PlayerController playerController = new PlayerController(Player.getInstance());
        mainArea.addKeyListener(playerController); //메인 영역에 키 리스너 추가
        mainArea.addMouseListener(playerController); //메인 영역에 마우스 리스너 추가
        mainArea.addMouseMotionListener(playerController);
        EntityManager.addEntity(Player.getInstance()); //엔티티 리스트에 플레이어 추가

        new Timer(10, evt -> EntityManager.onTick()).start(); //EntityManager 클래스의 onTick 정적 메서드 호출 쓰레드 생성 (0.01ms 마다 호출)
        new Timer(1, evt -> mainArea.validateArea()).start();
        new Timer(100, evt -> SpawnManager.onTick()).start();
    }
}
