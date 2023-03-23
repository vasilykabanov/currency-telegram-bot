package ru.vkabanov.currencytelegrambot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceMultiValuteResponse {

    @JsonProperty("USD")
    private String usd;

    @JsonProperty("EUR")
    private String eur;
}
