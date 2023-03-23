package ru.vkabanov.currencytelegrambot.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.vkabanov.currencytelegrambot.config.ClientRestApiLayerProperties;
import ru.vkabanov.currencytelegrambot.model.FixerLatestResponse;

@Slf4j
@Component
public class ApiLayerClientImpl extends RestClient implements ApiLayerClient {

    private final ClientRestApiLayerProperties properties;

    public ApiLayerClientImpl(RestTemplate restTemplate, ClientRestApiLayerProperties properties) {
        super(restTemplate);
        this.properties = properties;
    }

    @Cacheable(value = "getOtherCurrency", key = "#base")
    @Override
    public FixerLatestResponse getLatest(String symbols, String base) {
        log.info("Start send request to apilayer");

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(properties.getUrl())
                .pathSegment("fixer", "latest")
                .queryParam("symbols", symbols)
                .queryParam("base", base);

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", properties.getApiKey());
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<FixerLatestResponse> response = exchange(uriBuilder.toUriString(), HttpMethod.GET, entity,
                FixerLatestResponse.class);

        return response.getBody();
    }
}
