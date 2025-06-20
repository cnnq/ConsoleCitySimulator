package gui;

import org.jetbrains.annotations.NotNull;
import main.Game;

import javax.swing.*;

public class GameWindow extends JFrame {

    public static final int DEFAULT_WIDTH = 512 + 256;
    public static final int DEFAULT_HEIGHT = 512 + 256;

    private GameMode gameMode;


    public GameWindow(@NotNull Game game) {
        super("Game");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setGameMode(new EditMode(game));

        setVisible(true);
    }

    @NotNull
    public GameMode getGameMode() {
        return gameMode;
    }

    private void setGameMode(@NotNull GameMode newGameMode) {
        gameMode = newGameMode;
        setContentPane(newGameMode.getContentPane());
        pack();
    }
}
