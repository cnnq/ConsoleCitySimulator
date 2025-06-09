package modes;

import gui.*;
import layers.Layer;
import org.jetbrains.annotations.NotNull;
import main.Game;

import java.awt.*;

/**
 * Game mode that allows to edit layers
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

        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(0);

        container.setLayout(layout);

        topBar = new CityTopBar(this);
        map = new MapPanel(this);
        bottomBar = new BottomBar(this);

        container.add(topBar);
        container.add(map);
        container.add(bottomBar);

        setContentPane(container);
    }

    @NotNull
    public Game getGame() {
        return game;
    }

    public void setTopBar(@NotNull TopBar topBar) {
        Container container = getContentPane();
        container.remove(this.topBar);
        container.add(topBar, 0);
        container.revalidate();
        this.topBar = topBar;
    }

    @NotNull
    public TopBar getTopBar() {
        return topBar;
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
}
