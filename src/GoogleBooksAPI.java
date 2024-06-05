import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GoogleBooksAPI {
    private static final String API_KEY = "AIzaSyBl49c0G3ort8u0Fey5uljxkxE-rMUNe2E"; // Google Books API key
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public static List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        try {
            URL url = new URL(API_URL + query + "&key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(sb.toString());
            JSONArray itemsArray = (JSONArray) jsonResponse.get("items");
            if (itemsArray != null) {
                for (Object item : itemsArray) {
                    JSONObject bookObject = (JSONObject) ((JSONObject) item).get("volumeInfo");
                    String title = (String) bookObject.getOrDefault("title", "No Title");
                    JSONArray authorsArray = (JSONArray) bookObject.get("authors");
                    String author = (authorsArray != null && !authorsArray.isEmpty()) ? (String) authorsArray.get(0) : "Unknown Author";
                    JSONArray industryIdentifiers = (JSONArray) bookObject.get("industryIdentifiers");
                    String isbn = (industryIdentifiers != null && !industryIdentifiers.isEmpty()) ? (String) ((JSONObject) industryIdentifiers.get(0)).get("identifier") : "Unknown ISBN";
                    books.add(new Book(title, author, isbn));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
