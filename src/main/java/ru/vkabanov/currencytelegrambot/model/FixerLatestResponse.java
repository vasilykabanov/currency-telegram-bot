package ru.vkabanov.currencytelegrambot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerLatestResponse {

    private boolean success;

    private long timestamp;

    private String base;

    private String date;

    private FixerLatestRatesResponse rates;
}
