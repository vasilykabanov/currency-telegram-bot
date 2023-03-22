package ru.vkabanov.currencytelegrambot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "client.rest.cbr")
public class ClientRestCbrProperties {

    private String url;
}