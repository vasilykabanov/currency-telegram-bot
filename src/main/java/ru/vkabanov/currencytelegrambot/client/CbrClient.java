package ru.vkabanov.currencytelegrambot.client;

import ru.vkabanov.currencytelegrambot.model.xmldailyrs.ValCurs;

public interface CbrClient {

    ValCurs getCurrencyRates();
}
