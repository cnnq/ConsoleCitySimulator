package gui;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Upper element of the GUI which supports interactions witch the simulation.
 * {@link BottomBar} can change its appearance by switching current {@link TopBarInstance}.
 */
public class TopBar extends JPanel {

    private final HashMap<String, TopBarInstance> instances;
    private TopBarInstance currentInstance;

    private final CardLayout layout;

    private final EditMode editMode;


    public TopBar(@NotNull EditMode editMode) {
        this.editMode = editMode;

        layout = new CardLayout();
        setLayout(layout);

        instances = new HashMap<>();
        instances.put("CITY", new CityTopBar(this));
        instances.put("PIPES", new PipesTopBar(this));
        instances.put("WIRES", new WiresTopBar(this));
        instances.put("ECONOMY", new EconomyTopBar(this));

        for(var entry : instances.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }

        setCurrentTopBarInstance("CITY");
    }

    @NotNull
    public TopBarInstance getCurrentTopBarInstance() {
        return currentInstance;
    }

    public void setCurrentTopBarInstance(@NotNull String name) {
        if(!instances.containsKey(name)) throw new IllegalArgumentException("Unknown top bar instance: " + name);
        currentInstance = instances.get(name);
        layout.show(this, name);
    }

    @NotNull
    public EditMode getEditMode() {
        return editMode;
    }
}
