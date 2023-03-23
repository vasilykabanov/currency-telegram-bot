package ru.vkabanov.currencytelegrambot.client;

import ru.vkabanov.currencytelegrambot.model.PriceMultiResponse;

public interface CryptoCompareClient {

    PriceMultiResponse getPriceMulti();
}
