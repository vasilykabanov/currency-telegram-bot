package ru.vkabanov.currencytelegrambot.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.vkabanov.currencytelegrambot.config.ClientRestCbrProperties;
import ru.vkabanov.currencytelegrambot.model.xmldailyrs.ValCurs;
import ru.vkabanov.currencytelegrambot.service.XmlMessageService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CbrClientImpl implements CbrClient {

    private final RestTemplate restTemplate;

    private final ClientRestCbrProperties cbrProperties;

    private final XmlMessageService xmlMessageService;

    @Override
    public ValCurs getCurrencyRates() {
        log.debug("Start send request to cbr");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(cbrProperties.getUrl(), HttpMethod.GET, entity, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Failed while send request to cbr, statusCode: {}", response.getStatusCode());
                throw new RuntimeException(String.format("statusCode is %s", response.getStatusCode()));
            }

            if (response.getBody() == null) {
                log.error("Response body form google recaptcha is null or empty: {}", response);
                throw new RuntimeException("Empty response body");
            }

            return xmlMessageService.unmarshal(ValCurs.class, response.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
