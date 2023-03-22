package ru.vkabanov.currencytelegrambot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vkabanov.currencytelegrambot.service.CurrencyTelegramBot;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotInitializer {

    private final CurrencyTelegramBot currencyTelegramBot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(currencyTelegramBot);
        } catch (TelegramApiException e) {
            log.error("Error while register bot: {}" + e.getMessage());
        }
    }
}
