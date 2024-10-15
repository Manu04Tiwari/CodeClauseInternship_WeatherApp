import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class WeatherApp {

    private static final String API_KEY = "14239ee10f1a3996b0e48bef2e53c01b"; // Replace with your API key
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    // Method to get weather data based on city name
    public static String getWeatherData(String city) {
        StringBuilder result = new StringBuilder();

        try {
            // Build URL with city name and API key
            String urlString = WEATHER_URL + city + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // Success
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Close connections
                in.close();
                connection.disconnect();

                // Parse JSON response
                JsonObject weatherJson = JsonParser.parseString(content.toString()).getAsJsonObject();
                result.append(parseWeatherData(weatherJson));
            } else {
                result.append("Error fetching weather data for ").append(city);
            }
        } catch (Exception e) {
            result.append("Exception occurred: ").append(e.getMessage());
        }

        return result.toString();
    }

    // Method to parse weather data from JSON
    private static String parseWeatherData(JsonObject weatherJson) {
        StringBuilder parsedData = new StringBuilder();

        String cityName = weatherJson.get("name").getAsString();
        JsonObject mainData = weatherJson.get("main").getAsJsonObject();
        double temperature = mainData.get("temp").getAsDouble();
        int humidity = mainData.get("humidity").getAsInt();

        JsonObject weather = weatherJson.getAsJsonArray("weather").get(0).getAsJsonObject();
        String description = weather.get("description").getAsString();

        parsedData.append("City: ").append(cityName).append("\n")
                  .append("Temperature: ").append(temperature).append("°C\n")
                  .append("Humidity: ").append(humidity).append("%\n")
                  .append("Weather: ").append(description).append("\n");

        return parsedData.toString();
    }
}
