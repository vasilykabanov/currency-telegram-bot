package ru.vkabanov.currencytelegrambot.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.vkabanov.currencytelegrambot.config.ClientRestCryptoCompareProperties;
import ru.vkabanov.currencytelegrambot.model.PriceMultiResponse;

@Slf4j
@Component
public class CryptoCompareClientImpl extends RestClient implements CryptoCompareClient {

    private final ClientRestCryptoCompareProperties properties;

    public CryptoCompareClientImpl(RestTemplate restTemplate, ClientRestCryptoCompareProperties properties) {
        super(restTemplate);
        this.properties = properties;
    }

    @Override
    public PriceMultiResponse getPriceMulti() {
        log.info("Start send request to cryptocompare");

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(properties.getUrl())
                .pathSegment("data", "pricemulti")
                .queryParam("tsyms", "USD,EUR")
                .queryParam("fsyms", "BTC,ETH,LTC,XMR,DASH,WAN");

        ResponseEntity<PriceMultiResponse> response = exchange(uriBuilder.toUriString(), HttpMethod.GET, null,
                PriceMultiResponse.class);

        return response.getBody();
    }
}
