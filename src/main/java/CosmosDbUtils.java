import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class CosmosDbUtils {

    private final static Logger log = LoggerFactory.getLogger(CosmosDbUtils.class);

    public static CosmosClient getClient(String resource) {
        Properties properties = readConnectionProperties();
        if (resource == null) {
            resource = properties.getProperty("default.env.prefix");
        }
        return new CosmosClientBuilder()
                .endpoint(properties.getProperty(resource + ".azure.cosmosdb.uri"))
                .key(properties.getProperty(resource + ".azure.cosmosdb.key"))
                .consistencyLevel(ConsistencyLevel.SESSION)
                .gatewayMode()
                .buildClient();
    }

    private static Properties readConnectionProperties() {
        String fileName = "config.properties";
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(fileName)));
            return properties;
        } catch (IOException e) {
            log.error("File {} not found", fileName, e);
            throw new IllegalArgumentException(e);
        }
    }

    public static List<JsonNode> queryDocuments(Args args) {
        log.info("SQL query execution for: {}", args);
        try (CosmosClient client = getClient(args.env)) {
            return client.getDatabase(args.db)
                    .getContainer(args.collection)
                    .queryItems(args.sql, new CosmosQueryRequestOptions(), JsonNode.class)
                    .stream()
                    .map(CosmosDbUtils::removeNotDataFields)
                    .collect(Collectors.toList());
        }
    }

    public static JsonNode removeNotDataFields(JsonNode document) {
        if (document.isObject()) {
            ((ObjectNode) document).remove(Arrays.asList("_rid", "_self", "_etag", "_attachments", "_ts"));
        }
        return document;
    }
}