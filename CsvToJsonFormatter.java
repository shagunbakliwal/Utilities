import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvToJsonFormatter {

    public static List<Map<String, String>> readCsv(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();
            List<Map<String, String>> dataList = new ArrayList<>();

            if (rows.isEmpty()) return dataList;

            String[] headers = rows.get(0); // First row as headers

            for (int i = 1; i < rows.size(); i++) { // Loop through data rows
                Map<String, String> rowMap = new LinkedHashMap<>();
                String[] row = rows.get(i);
                for (int j = 0; j < headers.length; j++) {
                    rowMap.put(headers[j], j < row.length ? row[j] : ""); // Handle missing values
                }
                dataList.add(rowMap);
            }
            return dataList;
        }
    }

    public static String convertToJsonWithoutQuotes(List<Map<String, String>> data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(SerializationFeature.QUOTE_FIELD_NAMES, false);
        objectMapper.disable(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature());
        return objectMapper.writeValueAsString(data);
    }

    public static void main(String[] args) throws IOException, CsvException {
        String filePath = "data.csv"; // Replace with actual path
        List<Map<String, String>> csvData = readCsv(filePath);
        String jsonOutput = convertToJsonWithoutQuotes(csvData);

        System.out.println(jsonOutput);
    }
}
