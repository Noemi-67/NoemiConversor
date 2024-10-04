import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

class ApiResponse {
    String result;
    String base_code;
    Map<String, Double> conversion_rates;
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)throws Exception {
        Scanner scanner =new Scanner(System.in);
        String url = "https://v6.exchangerate-api.com/v6/e121a09f4e4c4f45b2901dbc/latest/USD";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(response.body(), ApiResponse.class);


        if (!"success".equals(apiResponse.result)) {
            System.out.println("Error al obtener los datos de la API.");
            return;
        }
        System.out.println("Bienvenido/a, aqui podrás consultar el cambio de moneda");
        System.out.println("Selecciona la moneda de destino (por ejemplo. ARS,BOB, BRL, COP,EUR,MXN, etc.):");
        String targetCurrency = scanner.nextLine().toUpperCase();
        System.out.println("Ingrese la cantidad en USD que desea convertir: ");
        double amount = scanner.nextDouble();


        if (apiResponse.conversion_rates.containsKey(targetCurrency)) {
            double rate = apiResponse.conversion_rates.get(targetCurrency);
            double convertedAmount = amount * rate;
            System.out.println("La tasa de conversión de USD a " + targetCurrency + " es: " + rate);
            System.out.println(amount + " USD equivale a " + convertedAmount + " " + targetCurrency);
        } else {
            System.out.println("Lo siento, no se encontró la moneda: " + targetCurrency);
        }

        scanner.close();
    }
}


