package gui;

import layers.Layer;
import org.jetbrains.annotations.NotNull;
import main.Game;

import java.awt.*;

/**
 * Game mode that allows editing layers
 */
public class EditMode extends GameMode {

    private final Game game;

    private TopBar topBar;
    private final MapPanel map;
    private final BottomBar bottomBar;

    private Layer layer;


    public EditMode(@NotNull Game game) {
        this.game = game;
        layer = game.getCityMap();

        // Configure GUI
        Container container = new Container();
        container.setPreferredSize(new Dimension(GameWindow.DEFAULT_WIDTH, GameWindow.DEFAULT_HEIGHT));

        container.setLayout(new BorderLayout());

        topBar = new TopBar(this);
        map = new MapPanel(this);
        bottomBar = new BottomBar(this);

        container.add(topBar, BorderLayout.NORTH);
        container.add(map, BorderLayout.CENTER);
        container.add(bottomBar, BorderLayout.SOUTH);

        setContentPane(container);
    }

    @NotNull
    public Game getGame() {
        return game;
    }

    /**
     * Get currently edited layer
     */
    @NotNull
    public Layer getLayer() {
        return layer;
    }

    /**
     * Set layer to be edited
     * @param layer layer
     */
    public void setLayer(@NotNull Layer layer) {
        this.layer = layer;
    }

    @NotNull
    public TopBar getTopBar() {
        return topBar;
    }
}
