package ru.vkabanov.currencytelegrambot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceMultiResponse {

    @JsonProperty("BTC")
    private PriceMultiValuteResponse btc;

    @JsonProperty("ETH")
    private PriceMultiValuteResponse eth;

    @JsonProperty("LTC")
    private PriceMultiValuteResponse ltc;

    @JsonProperty("XMR")
    private PriceMultiValuteResponse xmr;

    @JsonProperty("DASH")
    private PriceMultiValuteResponse dash;

    @JsonProperty("WAN")
    private PriceMultiValuteResponse wan;
}
