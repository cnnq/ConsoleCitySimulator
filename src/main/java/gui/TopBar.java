package gui;

import org.jetbrains.annotations.NotNull;
import other.Game;
import modes.EditMode;

import javax.swing.*;
import java.awt.*;

public class TopBar extends JMenuBar {

    private static final int TOP_BAR_HEIGHT = 32;

    private final EditMode editMode;

    private JLabel moneyLabel;


    public TopBar(@NotNull EditMode editMode) {
        setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, TOP_BAR_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.editMode = editMode;

        moneyLabel = new JLabel("Money: error", JLabel.CENTER);

        add(moneyLabel);
    }

    @Override
    public void paint(Graphics g) {
        moneyLabel.setText("Money: " + Double.toString(Math.round(editMode.getGameState().getMoney())) + "k $");
        super.paint(g);
    }
}
