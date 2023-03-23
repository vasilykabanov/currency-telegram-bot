package ru.vkabanov.currencytelegrambot.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.vkabanov.currencytelegrambot.config.ClientRestCbrProperties;
import ru.vkabanov.currencytelegrambot.model.xmldailyrs.ValCurs;
import ru.vkabanov.currencytelegrambot.service.XmlMessageService;

@Slf4j
@Component
public class CbrClientImpl extends RestClient implements CbrClient {

    private final ClientRestCbrProperties cbrProperties;

    private final XmlMessageService xmlMessageService;

    public CbrClientImpl(RestTemplate restTemplate, ClientRestCbrProperties cbrProperties,
                         XmlMessageService xmlMessageService) {
        super(restTemplate);
        this.cbrProperties = cbrProperties;
        this.xmlMessageService = xmlMessageService;
    }

    @Override
    public ValCurs getCurrencyRates() {
        log.info("Start send request to cbr");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = exchange(cbrProperties.getUrl(), HttpMethod.GET, entity, String.class);

        return xmlMessageService.unmarshal(ValCurs.class, response.getBody());
    }
}
