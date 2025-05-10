package modes;

import gui.BottomBar;
import layers.Layer;
import org.jetbrains.annotations.NotNull;
import other.Game;
import gui.MapPanel;
import gui.TopBar;
import other.GameState;

import java.awt.*;

/**
 * Game mode that allows to edit layers
 */
public class EditMode extends GameMode {

    private final GameState gameState;

    private final TopBar topBar;
    private final MapPanel map;
    private final BottomBar bottomBar;

    private Layer layer;


    public EditMode(@NotNull GameState gameState) {
        this.gameState = gameState;
        layer = gameState.getCityMap();

        // Configure GUI
        Container container = new Container();
        container.setPreferredSize(new Dimension(Game.DEFAULT_WIDTH, Game.DEFAULT_HEIGHT));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(0);

        container.setLayout(layout);

        topBar = new TopBar(this);
        map = new MapPanel(this);
        bottomBar = new BottomBar(this);

        container.add(topBar);
        container.add(map);
        container.add(bottomBar);

        setContentPane(container);
    }

    @NotNull
    public GameState getGameState() {
        return gameState;
    }

    @NotNull
    public Layer getLayer() {
        return layer;
    }

    public void setLayer(@NotNull Layer layer) {
        this.layer = layer;
    }
}
