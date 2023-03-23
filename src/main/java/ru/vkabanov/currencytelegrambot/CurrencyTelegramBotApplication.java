package ru.vkabanov.currencytelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CurrencyTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyTelegramBotApplication.class, args);
    }

}
