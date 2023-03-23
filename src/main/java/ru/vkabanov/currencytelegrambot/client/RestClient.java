package ru.vkabanov.currencytelegrambot.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class RestClient {

    private final RestTemplate restTemplate;

    protected <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        try {
            ResponseEntity<?> response = restTemplate.exchange(url, method, requestEntity, responseType);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Failed while send request, statusCode: {}", response.getStatusCode());
                throw new RuntimeException(String.format("statusCode is %s", response.getStatusCode()));
            }

            if (response.getBody() == null) {
                log.error("Response body is null or empty: {}", response);
                throw new RuntimeException("Empty response body");
            }

            return (ResponseEntity<T>) response;
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
