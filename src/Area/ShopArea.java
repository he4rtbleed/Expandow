package Area;

import Entity.Player;
import Utils.GameConditions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class ShopArea extends Area {
    private static ShopArea instance;

    // 업그레이드 항목 및 현재 상태를 저장하는 스태틱 배열
    private static final String[] upgradeItems = {"공격속도", "확장력", "공격력", "이동속도", "멀티샷"};
    private static final int[] upgradeLevels = {0, 0, 0, 0, 0};
    private static final int[] upgradeCosts = {30, 20, 40, 15, 45}; // 초기 비용 설정
    private static int refreshCost = 10;
    private static int[] currentItems = {0, 0, 0};
    private static Random random = new Random();

    private JButton upgradeButton1, upgradeButton2, upgradeButton3, refreshButton;

    public static ShopArea getInstance() {
        if (instance == null) {
            instance = new ShopArea();
        }
        return instance;
    }

    public ShopArea() {
        GameConditions.isGamePaused = true;

        setTitle("ShopArea");
        setSize(new Dimension(1000, 300));
        setLocationRelativeTo(null);

        setVisible(true);

        requestFocus();
        setAutoRequestFocus(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                GameConditions.isGamePaused = false;
                setVisible(false);
            }
        });

        for (int i = 0; i < 3; i ++) {
            currentItems[i] = random.nextInt(upgradeItems.length);
        }

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));

        // 좌측 버튼 추가
        upgradeButton1 = new JButton(upgradeItems[currentItems[0]] + " 업그레이드 (비용: " + upgradeCosts[currentItems[0]] + ")");
        upgradeButton1.addActionListener(new UpgradeButtonListener(0));
        centerPanel.add(upgradeButton1);

        // 중앙 버튼 추가
        upgradeButton2 = new JButton(upgradeItems[currentItems[1]] + " 업그레이드 (비용: " + upgradeCosts[currentItems[1]] + ")");
        upgradeButton2.addActionListener(new UpgradeButtonListener(1));
        centerPanel.add(upgradeButton2);

        // 우측 버튼 추가
        upgradeButton3 = new JButton(upgradeItems[currentItems[2]] + " 업그레이드 (비용: " + upgradeCosts[currentItems[2]] + ")");
        upgradeButton3.addActionListener(new UpgradeButtonListener(2));
        centerPanel.add(upgradeButton3);

        // 새로고침 버튼 추가
        refreshButton = new JButton("새로고침 (비용: " + refreshCost + ")");
        refreshButton.addActionListener(_ -> refreshShop(-1));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void showShop() {
        GameConditions.isGamePaused = true;
        setVisible(true);
    }

    private void refreshShop(int i) {
        if (i == -1) {
            if (Player.getInstance().getPoint() >= refreshCost) {
                Player.getInstance().addPoint(-refreshCost);
                refreshCost += 10;
                for (int j = 0; j < 3; j++) {
                    currentItems[j] = random.nextInt(upgradeItems.length);
                }
            }
        } else {
            currentItems[i] = random.nextInt(upgradeItems.length);
        }

        upgradeButton1.setText(upgradeItems[currentItems[0]] + "(" + upgradeLevels[currentItems[0]] + ")" + " 업그레이드 (비용: " + upgradeCosts[currentItems[0]] + ")");
        upgradeButton2.setText(upgradeItems[currentItems[1]] + "(" + upgradeLevels[currentItems[1]] + ")" +  " 업그레이드 (비용: " + upgradeCosts[currentItems[1]] + ")");
        upgradeButton3.setText(upgradeItems[currentItems[2]] + "(" + upgradeLevels[currentItems[2]] + ")" +  " 업그레이드 (비용: " + upgradeCosts[currentItems[2]] + ")");
        refreshButton.setText("새로고침 (비용: " + refreshCost + ")");
    }

    private static class UpgradeButtonListener implements ActionListener {
        private int index;

        public UpgradeButtonListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Player.getInstance().getPoint() >= upgradeCosts[currentItems[index]]) {
                Player.getInstance().addPoint(-upgradeCosts[currentItems[index]]);
                upgradeLevels[currentItems[index]]++;
                upgradeCosts[currentItems[index]] += 20 * upgradeLevels[currentItems[index]];

                //"공격속도", "확장력", "공격력", "이동속도", "멀티샷"
                switch (currentItems[index]) {
                    case 0:
                        switch (upgradeLevels[currentItems[index]]) {
                            case 1 -> Player.getInstance().setAttackSpeed(0.4f);
                            case 2 -> Player.getInstance().setAttackSpeed(0.35f);
                            case 3 -> Player.getInstance().setAttackSpeed(0.3f);
                            case 4 -> Player.getInstance().setAttackSpeed(0.25f);
                            case 5 -> Player.getInstance().setAttackSpeed(0.2f);
                            case 6 -> Player.getInstance().setAttackSpeed(0.15f);
                            case 7 -> Player.getInstance().setAttackSpeed(0.1f);
                            case 8 -> Player.getInstance().setAttackSpeed(0.05f);
                            case 9 -> Player.getInstance().setAttackSpeed(0.04f);
                        }
                        break;
                    case 1:
                        Player.getInstance().addExpandForce(5);
                        break;
                    case 2:
                        Player.getInstance().addAttackDamage(7);
                        break;
                    case 3:
                        Player.getInstance().addMoveSpeed(0.5f);
                        break;
                    case 4:
                        Player.getInstance().addMultiShot(1);
                        break;
                }
                ShopArea.getInstance().refreshShop(index);
            }
        }
    }
}
