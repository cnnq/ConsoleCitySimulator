package gui;

import other.Game;
import other.GameState;

import javax.swing.*;
import java.awt.*;

public class TopBar extends JMenuBar {

    private static final int TOP_BAR_HEIGHT = 32;

    private JLabel moneyLabel;

    public TopBar() {
        setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, TOP_BAR_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        moneyLabel.setText("Money: " + Double.toString(Math.round(GameState.getMoney())) + "k $");
        super.paint(g);
    }
}
