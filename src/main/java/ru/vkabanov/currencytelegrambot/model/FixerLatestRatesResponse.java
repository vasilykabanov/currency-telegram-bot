package ru.vkabanov.currencytelegrambot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerLatestRatesResponse {

    @JsonProperty("EUR")
    private BigDecimal eur;

    @JsonProperty("GBP")
    private BigDecimal gbr;

    @JsonProperty("JPY")
    private BigDecimal jpy;

    @JsonProperty("CHF")
    private BigDecimal chf;

    @JsonProperty("CNY")
    private BigDecimal cny;
}
