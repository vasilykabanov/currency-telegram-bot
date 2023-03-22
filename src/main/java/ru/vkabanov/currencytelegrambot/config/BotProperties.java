package ru.vkabanov.currencytelegrambot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("bot")
public class BotProperties {

    private String name;

    private String token;
}
