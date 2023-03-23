package ru.vkabanov.currencytelegrambot.client;

import ru.vkabanov.currencytelegrambot.model.FixerLatestResponse;

public interface ApiLayerClient {

    FixerLatestResponse getLatest(String symbols, String base);
}
