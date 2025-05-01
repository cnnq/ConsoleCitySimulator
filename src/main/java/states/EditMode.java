package states;

import other.Game;
import other.MapPanel;

import javax.swing.*;
import java.awt.*;

public class EditMode extends GameMode {

    private static final int TOP_BAR_HEIGHT = 32;
    private static final int BOTTOM_BAR_HEIGHT = 32;

    private final MapPanel map;


    public EditMode() {
        Container container = new Container();
        container.setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, Game.DEFAULT_HEIGHT));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(0);

        container.setLayout(layout);

        JLabel top = new JLabel("Top", JLabel.CENTER);
        top.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        top.setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, TOP_BAR_HEIGHT));

        map = new MapPanel();

        JLabel bottom = new JLabel("Bottom", JLabel.CENTER);
        bottom.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bottom.setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, BOTTOM_BAR_HEIGHT));

        container.add(top);
        container.add(map);
        container.add(bottom);

        setContentPane(container);
    }
}
