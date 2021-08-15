import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.List;

public class JsonUtils {

    private static final ObjectMapper objectMapper = initObjectMapperWithIndentation();

    private static ObjectMapper initObjectMapperWithIndentation() {
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter()
                .withObjectIndenter(indenter);
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .setDefaultPrettyPrinter(printer);
    }

    public static String convertToJsonString(List<JsonNode> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not convert object to json string: " + e.getMessage());
        }
    }
}
