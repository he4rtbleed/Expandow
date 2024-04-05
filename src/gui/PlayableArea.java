package gui;

import javax.swing.*;

public class PlayableArea extends JFrame {
   final int START_WIDTH = 300;
   final int START_HEIGHT = 300;
   PlayableArea() {
      setSize(START_WIDTH, START_HEIGHT);
      setLocationRelativeTo(null);

      setVisible(true);
   }
}
