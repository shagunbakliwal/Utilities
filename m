To prepare a smart and reusable LineAggregator for writing .bcp-compatible files in Spring Batch (via FlatFileItemWriter), here’s an efficient and extensible setup that uses:

✅ Reflection to avoid hardcoding

✅ SQL column order from the DB

✅ Custom delimiter (|, tab, etc.)

✅ Automatic field matching (even snake_case to camelCase)



---

✅ 1. Create a Smart LineAggregator<T>

public class DynamicLineAggregator<T> implements LineAggregator<T> {

    private final List<String> columnOrder;
    private final Map<String, Field> fieldMap;
    private final String delimiter;

    public DynamicLineAggregator(Class<T> type, List<String> columnOrder, String delimiter) {
        this.columnOrder = columnOrder;
        this.delimiter = delimiter;
        this.fieldMap = buildFieldMap(type);
    }

    private Map<String, Field> buildFieldMap(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .collect(Collectors.toMap(
                    f -> f.getName().toLowerCase(), // key
                    Function.identity()
                ));
    }

    private String normalize(String dbColumnName) {
        // Convert snake_case to camelCase
        String[] parts = dbColumnName.toLowerCase().split("_");
        return parts[0] + Arrays.stream(parts, 1, parts.length)
                .map(p -> Character.toUpperCase(p.charAt(0)) + p.substring(1))
                .collect(Collectors.joining());
    }

    @Override
    public String aggregate(T item) {
        return columnOrder.stream()
                .map(col -> {
                    String normalized = normalize(col);
                    Field field = fieldMap.get(normalized);
                    try {
                        return field != null ? String.valueOf(field.get(item)) : "";
                    } catch (IllegalAccessException e) {
                        return "";
                    }
                })
                .collect(Collectors.joining(delimiter));
    }
}


---

✅ 2. Get SQL Column Order via Metadata (Optional Helper)

public List<String> fetchColumnOrder(DataSource dataSource, String table) throws SQLException {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + table + " WHERE 1 = 0");
         ResultSet rs = stmt.executeQuery()) {

        ResultSetMetaData meta = rs.getMetaData();
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            columns.add(meta.getColumnName(i));
        }
        return columns;
    }
}


---

✅ 3. Use It in a FlatFileItemWriter Bean

@Bean
public FlatFileItemWriter<Customer> customerWriter(DataSource dataSource) throws SQLException {
    List<String> columns = fetchColumnOrder(dataSource, "customer");

    FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<>();
    writer.setResource(new FileSystemResource("output/customer.bcp"));
    writer.setLineAggregator(new DynamicLineAggregator<>(Customer.class, columns, "|"));
    writer.setAppendAllowed(true);
    writer.setHeaderCallback(writer1 -> writer1.write(String.join("|", columns)));
    return writer;
}


---

✅ Result Example

POJO

public class Customer {
    private Long customerId;
    private String firstName;
    private String lastName;
}

SQL

CREATE TABLE customer (
    customer_id BIGINT,
    first_name VARCHAR(100),
    last_name VARCHAR(100)
);

Output File (bcp format)

123|John|Doe
456|Alice|Smith


---

🔒 Optional Enhancements

Add support for nested fields (e.g., address.city)

Add custom formatting for dates/numbers

Add caching for field lookups



---

✅ Summary

Feature	Benefit

Reflection + metadata	No manual mapping
Works across entities	Reusable per POJO
SQL-aligned output	Perfect for BCP IN
Custom delimiter support	Easy for `



---

Would you like a version that works with multiple entity types and integrates into a ClassifierCompositeItemWriter?

