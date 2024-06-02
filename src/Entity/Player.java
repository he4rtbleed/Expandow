package Entity;

import Area.MainArea;
import Entity.Mobs.Mobs;
import Utils.CoordinateConvertHelper;
import Utils.IntersectHelpers;

import java.awt.*;

public class Player extends Entity implements Movable {
    private static Player Instance = null;
    public boolean[] keyMapped = { false, false, false, false }; //방향키 상, 하, 좌, 우
    public Point mousePos = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private long lastMissileFired = -1; //플레이어의 미사일 공격속도를 위해 정의
    private long lastHitProcessed = -1; //HP 줄어든뒤 잠시 무적시간을 위해 정의


    private int point = 0; //업그레이드를 위한 돈 변수 정의
    private int totalPoints = 0; //난이도조절을위한 포인트


    // 밑에서부터 업그레이드 가능 속성들
    private int maxHp = 100;
    private float attackSpeed = 0.4f; //0.1 이면 0.1s 즉 100ms 마다 미사일 발사
    private int expandForce = 25; //영역확장력
    private int attackDamage = 10;
    private int multiShot = 1;

    public int getPoint() {
        return point;
    }

    public int getTotalPoints() { return totalPoints; }

    public void addPoint(int amount) {
        this.point += amount;
        this.totalPoints += amount;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void addMaxHp(int amount) {
        this.maxHp += amount;
    }

    public int getExpandForce() {
        return expandForce / multiShot;
    }

    public void addExpandForce(int amount) { this.expandForce += amount; }

    public void setAttackSpeed(float speed) { this.attackSpeed = speed; }

    public int getAttackDamage() { return this.attackDamage; }
    public void addAttackDamage(int amount) { this.attackDamage += amount; }

    public void addMoveSpeed(float amount) { this.speed += amount; }

    public void addMultiShot(int amount) { this.multiShot += amount; }

    //업그레이드 가능 속성 끝



    public Player(Point pos) {
        this.absPos = pos;
        this.collisionSize = new Point(30, 30);
        this.speed = 3.f;
        Instance = this;
    }

    public static Player getInstance() {
        return Instance;
    }

    private void checkAndFireMissile() {
        // 미사일 발사 로직
        if (mousePos.x != Integer.MAX_VALUE && mousePos.y != Integer.MAX_VALUE) {
            if (System.currentTimeMillis() - lastMissileFired > attackSpeed * 1000.f) { //최근 발사 이후 공격속도sec 만큼 지나고 나서 다음 미사일 발사
                lastMissileFired = System.currentTimeMillis();
                for (int i = 0; i < multiShot; i++) {
                    Point tempStartPos = new Point(absPos.x + 5 * i, absPos.y + 5 * i);
                    Point tempPos = new Point(mousePos.x + 5 * i, mousePos.y + 5 * i);
                    PlayerMissile missile = new PlayerMissile(this, tempStartPos,
                            CoordinateConvertHelper.GetAngleFromTwoPoints(tempStartPos, tempPos));
                    EntityManager.addEntity(missile);
                }
            }
        }
    }

    @Override
    public void move() {
        this.absPos.x += this.direction.x * speed;
        this.absPos.y += this.direction.y * speed;
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

        checkAndFireMissile();

        onCollideCallback();
    }

    @Override
    public void onCollideCallback() {
        super.onCollideCallback();

        // 충돌 검사 로직
        if (System.currentTimeMillis() - lastHitProcessed > 1000) {
            for (Entity mob : EntityManager.getMobsList()) {
                if (IntersectHelpers.isCollide(this, mob)) {
                    this.HP -= 10;
                    lastHitProcessed = System.currentTimeMillis();
                    ((Mobs)mob).lastHitProcessed = System.currentTimeMillis();
                    break;
                }
            }

            for (Entity mobMissiles : EntityManager.getMobMissilesList()) {
                if (IntersectHelpers.isCollide(this, mobMissiles)) {
                    this.HP -= 10;
                    lastHitProcessed = System.currentTimeMillis();
                    EntityManager.removeEntity(mobMissiles);
                    break;
                }
            }
        }
    }

    @Override
    public void onPaint(Graphics g) {
        if (System.currentTimeMillis() - this.lastHitProcessed < 1000) //1000ms동안 히트이펙트 표시
            g.setColor(Color.gray);
        else
            g.setColor(Color.black);
        g.fillOval(absPos.x, absPos.y, 30, 30);
    }
}
