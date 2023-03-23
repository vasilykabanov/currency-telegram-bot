package ru.vkabanov.currencytelegrambot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "client.rest.crypto-compare")
public class ClientRestCryptoCompareProperties {

    private String url;
}