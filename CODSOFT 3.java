import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // User Input
            System.out.print("Enter Base Currency (e.g., USD): ");
            String baseCurrency = sc.next().toUpperCase();

            System.out.print("Enter Target Currency (e.g., INR): ");
            String targetCurrency = sc.next().toUpperCase();

            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            // API URL
            String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + baseCurrency;

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Parse JSON Response
            JSONObject json = new JSONObject(response.toString());
            JSONObject rates = json.getJSONObject("rates");

            double exchangeRate = rates.getDouble(targetCurrency);

            // Conversion
            double convertedAmount = amount * exchangeRate;

            // Display Result
            System.out.println("\n----- Currency Conversion Result -----");
            System.out.println("Base Currency   : " + baseCurrency);
            System.out.println("Target Currency : " + targetCurrency);
            System.out.println("Exchange Rate   : " + exchangeRate);
            System.out.println("Converted Amount: " + convertedAmount + " " + targetCurrency);

        } catch (Exception e) {
            System.out.println("Error: Unable to fetch exchange rates.");
            e.printStackTrace();
        }

        sc.close();
    }
}