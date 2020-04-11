package eu.lukasschoerghuber.publictransporthero.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class PublicTransportHeroTelegramBot extends TelegramLongPollingBot {
    private final String apiToken;
    private final String botUsername;

    public PublicTransportHeroTelegramBot(
            @Value("${telegram.bot.apiToken}") String apiToken,
            @Value("${telegram.bot.username}") String botUsername
    ) {
        this.apiToken = apiToken;
        this.botUsername = botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if ((message != null) && (message.hasText())) {
            var sendMessageRequest = new SendMessage();
            sendMessageRequest.setText(message.getFrom().getFirstName());
            sendMessageRequest.setChatId(message.getChatId());
            try {
                this.execute(sendMessageRequest);
            } catch (TelegramApiException exception) {
                // TODO: log error
                exception.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.apiToken;
    }
}
