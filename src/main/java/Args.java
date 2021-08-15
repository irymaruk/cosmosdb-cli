import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = {"--database", "-d"}, description = "Database name", required = true, order = 1)
    public String db;

    @Parameter(names = {"--collection", "-c"}, description = "Collection name", required = true, order = 2)
    public String collection;

    @Parameter(names = {"--sql", "-s"}, description = "SQL string (in double quotes)", required = true, order = 3)
    public String sql;

    @Parameter(names = {"--env", "-e"}, description = "Environment prefix. If not specified then default.env.prefix from config.properties will be used", order = 4)
    public String env;

    @Parameter(names = {"--format", "-f"}, description = "Output format JSON or CSV (default JSON)", order = 5)
    public String format = "JSON";

    @Parameter(names = {"--help", "-h"}, help = true, description = "Show usage")
    public boolean help;

    @Override
    public String toString() {
        return "Args{" +
                "env='" + env + '\'' +
                ", db='" + db + '\'' +
                ", collection='" + collection + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
