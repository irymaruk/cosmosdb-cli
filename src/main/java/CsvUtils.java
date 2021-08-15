import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.util.List;

public class CsvUtils {
    private static final CsvMapper csvMapper = new CsvMapper();

    public static String convertToCsvString(List<JsonNode> list) {
        if (list.isEmpty()) {
            return "[]";
        }
        try {
            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            JsonNode firstObject = list.get(0);
            firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
            return csvMapper
                    .writerFor(java.util.List.class)
                    .with(csvSchema)
                    .writeValueAsString(list);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not convert object to csv string: " + e.getMessage());
        }
    }
}
