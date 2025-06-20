package infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Map;

/**
 * Class for loading and managing infrastructure
 */
public class InfrastructureManager {

    private static final String DEFAULT_INFRASTRUCTURE_PATH = "res/infrastructure.json";

    public static InfrastructureManager INSTANCE = new InfrastructureManager();

    private Map<String, Infrastructure> infrastructureMap;


    private InfrastructureManager() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FileReader fileReader = new FileReader(DEFAULT_INFRASTRUCTURE_PATH);
            infrastructureMap = objectMapper.readValue(fileReader, new TypeReference<>() {});

        } catch (IOException e) {
            throw new InfrastructureNotLoadedException(DEFAULT_INFRASTRUCTURE_PATH);
        }
    }

    /**
     * Returns infrastructure of given name. Null if value could not be found.
     * @param name name of searched infrastructure
     */
    public Infrastructure getInfrastructure(@NotNull String name) {
        return infrastructureMap.get(name);
    }

    /**
     * Returns infrastructure of given name and type. Null if value could not be found.
     * @param name name of searched infrastructure
     * @param type type to cast to
     */
    public <T extends Infrastructure> T getInfrastructure(@NotNull String name, Class<T> type) {
        Infrastructure infrastructure = infrastructureMap.get(name);
        if (type.isInstance(infrastructure)) {
            return type.cast(infrastructure);
        }
        return null;
    }
}
