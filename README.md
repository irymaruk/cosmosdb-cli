# Azure CosmosDB command line client for SQL queries

### Preconditions:

1. `mvn clean package`
2. Rename _cosmosdb-cli-jar-with-dependencies.jar_ into _cosmosdb-cli.jar_ and put along with _config.properties_ into
   one folder
3. Update uir and key in _config.properties_

### Usage examples:

1 - show usage:
> java -jar cosmosdb-cli.jar

```
CosmosDB command line client v 1.0
Place config.properties and cosmosdb-cli.jar to the same folder
Usage: java -jar cosmosdb-cli.jar [options]
  Options:
  * --database, -d
      Database name
  * --collection, -c
      Collection name
  * --sql, -s
      SQL string (in double quotes)
    --env, -e
      Environment prefix. If not specified then default.env.prefix from 
      config.properties will be used
    --format, -f
      Output format JSON or CSV (default JSON)
      Default: JSON
    --help, -h
      Show usage

Examples:
java -jar cosmosdb-cli.jar --database rxr-rxi-dev-purchaseordermanagement --collection POM_Product --sql "SELECT top 2 c.productCode FROM POM_Product as c"
java -jar cosmosdb-cli.jar -f csv -e devqe -d rxr-rxi-devqe-purchaseordermanagement -c POM_Product -s "SELECT top 2 c.productCode, c.productCoding.upc, c.drugClassFederal, c.itemDescription FROM POM_Product as c"
```

### Output

SQL result will be print to the console. Program logs will be stored in the file *cosmosdb-cli.log*

Example 1 (json)

````
[ {
    "productCode" : "11183",
    "drugClassFederal" : "NA",
    "itemDescription" : "HYDROCO-APAP 5/325MG TAB(TRI)1000"
}, {
    "productCode" : "16293",
    "drugClassFederal" : "NA",
    "itemDescription" : "PROGESTERONE 200MG CAPS (DR)100"
} ]

````

Example 2 (csv)

```
productCode,drugClassFederal,itemDescription
11183,NA,"HYDROCO-APAP 5/325MG TAB(TRI)1000"
16293,NA,"PROGESTERONE 200MG CAPS (DR)100"
```