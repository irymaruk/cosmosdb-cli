import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] input) {
//        input = new String[]{"--help"};
//        input = new String[]{"--database", "dev-purchaseordermanagement", "--collection", "Product", "--sql", "SELECT top 2 c.productCode, c.productCoding.upc, c.drugClassFederal, c.itemDescription FROM Product as c "};
//        input = new String[]{"-f", "CSV", "-e", "devqe", "-d", "devqe-purchaseordermanagement", "-c", "Product", "-s", "SELECT top 2 c.productCode, c.productCoding.upc, c.drugClassFederal, c.itemDescription FROM Product as c"};

        Args args = new Args();
        JCommander jc = JCommander.newBuilder()
                .programName("java -jar cosmosdb-cli.jar")
                .addObject(args)
                .build();
        if (input.length == 0) {
            showUsageAndExit(jc);
        }
        jc.parse(input);

        if (args.help) {
            showUsageAndExit(jc);
        }
        List<JsonNode> result = CosmosDbUtils.queryDocuments(args);
        printToConsole(result, args.format);
    }

    private static void showUsageAndExit(JCommander jc) {
        log.info("CosmosDB command line client v 1.0");
        log.info("Place config.properties and cosmosdb-cli.jar to the same folder");
        jc.usage();
        log.info("Examples:");
        log.info("java -jar cosmosdb-cli.jar --database dev-purchaseordermanagement --collection Product --sql \"SELECT top 2 c.productCode FROM Product as c\"");
        log.info("java -jar cosmosdb-cli.jar -f csv -e devqe -d devqe-purchaseordermanagement -c Product -s \"SELECT top 2 c.productCode, c.productCoding.upc, c.drugClassFederal, c.itemDescription FROM Product as c\"");
        System.exit(0);
    }

    private static void printToConsole(List<JsonNode> result, String format) {
        String formattedStr = "CSV".equalsIgnoreCase(format)
                ? CsvUtils.convertToCsvString(result)
                : JsonUtils.convertToJsonString(result);
        log.info(formattedStr);
    }
}
