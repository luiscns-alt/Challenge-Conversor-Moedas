package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class CurrencyConverter {
    private static final ExchangeService exchangeService = new ExchangeService();
    private static final String[] BASE_CURRENCIES = {
            "USD - Dólar Americano",
            "EUR - Euro",
            "GBP - Libra Esterlina",
            "JPY - Iene Japonês",
            "BRL - Real Brasileiro",
            "AUD - Dólar Australiano",
            "CAD - Dólar Canadense"
    };
    private static final String[] TARGET_CURRENCIES = {
            "USD - Dólar Americano",
            "EUR - Euro",
            "GBP - Libra Esterlina",
            "JPY - Iene Japonês",
            "BRL - Real Brasileiro",
            "AUD - Dólar Australiano",
            "CAD - Dólar Canadense"
    };

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Selecione a moeda base das seguintes opções:");
            for (int i = 0; i < BASE_CURRENCIES.length; i++) {
                System.out.printf("%d. %s\n", i + 1, BASE_CURRENCIES[i]);
            }

            System.out.println("Digite o número da sua escolha para a moeda base:");
            int baseChoice = Integer.parseInt(reader.readLine());
            String baseCurrencyCode = BASE_CURRENCIES[baseChoice - 1].substring(0, 3);

            CurrencyResponse rates = exchangeService.getRates(baseCurrencyCode);

            System.out.println("Selecione a moeda alvo das seguintes opções:");
            for (int i = 0; i < TARGET_CURRENCIES.length; i++) {
                System.out.printf("%d. %s\n", i + 1, TARGET_CURRENCIES[i]);
            }

            System.out.println("Digite o número da sua escolha para a moeda alvo:");
            int targetChoice = Integer.parseInt(reader.readLine());
            String targetCurrencyCode = TARGET_CURRENCIES[targetChoice - 1].substring(0, 3);

            System.out.println("Digite o valor a ser convertido:");
            double amount = Double.parseDouble(reader.readLine());

            Map<String, Double> rateMap = rates.getConversionRates();
            Double rate = rateMap.get(targetCurrencyCode);
            if (rate != null) {
                double convertedAmount = amount * rate;
                System.out.printf("%.2f %s é equivalente a %.2f %s\n", amount, baseCurrencyCode, convertedAmount, targetCurrencyCode);
            } else {
                System.out.println("Código da moeda não encontrado.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Formato de número inválido. Por favor, digite um número válido.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Escolha inválida. Por favor, selecione um número do menu.");
        }
    }
}