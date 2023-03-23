package ru.vkabanov.currencytelegrambot.service;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vkabanov.currencytelegrambot.config.BotProperties;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyTelegramBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final GetCurrencyRatesService getCurrencyRates;

    private final static String START_TEXT = "Привет, %s, в этом боте ты всегда можешь узнать актуальный курс валют. :blush:";
    private final static String HELP_TEXT = "Help text....";
    private final static String NOT_FOUND_COMMAND_TEXT = "Извините, данная команда не поддерживается";

    private static final String YES_BUTTON = "YES_BUTTON";
    private static final String NO_BUTTON = "NO_BUTTON";

    @PostConstruct
    public void setBasicCommands() {
        List<BotCommand> commands = Arrays.asList(
                new BotCommand("/start", "get a welcome message"),
                new BotCommand("/register", "register"),
                new BotCommand("/mydata", "get your data stored"),
                new BotCommand("/deletedata", "delete my data"),
                new BotCommand("/help", "info how to use this bot"),
                new BotCommand("/settings", "set your preferences"));

        try {
            this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            log.info("Got message: '{}'. chatId: {}", messageText, chatId);

            switch (messageText) {
                case "/start" -> {
                    String name = update.getMessage().getChat().getFirstName();
                    String answer = EmojiParser.parseToUnicode(String.format(START_TEXT, name));

                    prepareAndSendMessage(chatId, answer);
                }
                case "/register" -> register(chatId);
                case "/help" -> prepareAndSendMessage(chatId, HELP_TEXT);
                case "\uD83C\uDDF7\uD83C\uDDFA RUB" -> prepareAndSendMessage(chatId, getCurrencyRates.getRubCurrency());
                case "\uD83C\uDF0D Прочие" -> prepareAndSendMessage(chatId, getCurrencyRates.getOtherCurrency());
                case "\uD83D\uDCC8 Криптовалюта" -> prepareAndSendMessage(chatId, getCurrencyRates.getCryptoCurrency());
                default -> prepareAndSendMessage(chatId, NOT_FOUND_COMMAND_TEXT);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals(YES_BUTTON)) {
                executeEditMessageText("You pressed YES button", chatId, messageId);
            } else if (callbackData.equals(NO_BUTTON)) {
                executeEditMessageText("You pressed NO button", chatId, messageId);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    private void executeMessage(BotApiMethod<?> message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error while send message: {}", e.getMessage());
        }
    }

    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        executeMessage(message);
    }

    private void register(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Do you really want to register?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();
        yesButton.setText("Yes");
        yesButton.setCallbackData(YES_BUTTON);
        var noButton = new InlineKeyboardButton();
        noButton.setText("No");
        noButton.setCallbackData(NO_BUTTON);
        rowInLine.add(yesButton);
        rowInLine.add(noButton);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        executeMessage(message);
    }

    private void prepareAndSendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("\uD83C\uDDF7\uD83C\uDDFA RUB");
        row.add("\uD83C\uDDFA\uD83C\uDDE6 UAH");
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add("\uD83C\uDF0D Прочие");
        row.add("\uD83D\uDCC8 Криптовалюта");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);

        executeMessage(message);
    }
}
