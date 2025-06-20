package infrastructure;

import org.jetbrains.annotations.NotNull;

public class InfrastructureNotLoadedException extends RuntimeException {
    public InfrastructureNotLoadedException(@NotNull String path) {
        super("Infrastructure " + path + " was not loaded");
    }
}
