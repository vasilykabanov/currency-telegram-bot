package ru.vkabanov.currencytelegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vkabanov.currencytelegrambot.client.ApiLayerClient;
import ru.vkabanov.currencytelegrambot.client.CbrClient;
import ru.vkabanov.currencytelegrambot.model.FixerLatestResponse;
import ru.vkabanov.currencytelegrambot.model.xmldailyrs.ValCurs;
import ru.vkabanov.currencytelegrambot.model.xmldailyrs.Valute;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCurrencyRatesService {

    private final CbrClient cbrClient;

    private final ApiLayerClient apiLayerClient;

    public String getRubCurrency() {
        ValCurs rates = cbrClient.getCurrencyRates();
        List<Valute> valutes = rates.getValute();

        StringBuilder builder = new StringBuilder();
        builder.append("\uD83C\uDDF7\uD83C\uDDFA Российский рубль\n\n");

        valutes.forEach(i -> {
            switch (i.getCharCode()) {
                case "USD" -> builder.append(String.format("🇺🇸 %s/RUB %s\n", i.getCharCode(), i.getValue()));
                case "EUR" -> builder.append(String.format("\uD83C\uDDEA\uD83C\uDDFA %s/RUB %s\n", i.getCharCode(), i.getValue()));
                case "CNY" -> builder.append(String.format("\uD83C\uDDE8\uD83C\uDDF3 %s/RUB %s\n", i.getCharCode(), i.getValue()));
                case "JPY" -> builder.append(String.format("\uD83C\uDDEF\uD83C\uDDF5 %s/RUB %s\n", i.getCharCode(), i.getValue()));
            }
        });

        return builder.toString();
    }

    public String getOtherCurrency() {
        FixerLatestResponse response = apiLayerClient.getLatest("EUR,GBP,JPY,CHF,CNY", "USD");

        return "\uD83C\uDF0D Прочие валюты\n\n" +
                String.format("🇪🇺 EUR/USD %s\n", response.getRates().getEur()) +
                String.format("🇬🇧 GBP/USD %s\n", response.getRates().getGbr()) +
                String.format("🇨🇭 CHF/USD %s\n", response.getRates().getChf()) +
                String.format("🇯🇵 JPY/USD %s\n", response.getRates().getJpy()) +
                String.format("🇨🇳 CNY/USD %s\n", response.getRates().getCny());
    }

    void getCryptoCurrency() {

    }
}
