package eu.lukasschoerghuber.publictransporthero.telegram;

import eu.lukasschoerghuber.publictransporthero.telegram.context.ChatbotContext;
import eu.lukasschoerghuber.publictransporthero.telegram.context.ChatbotContextProvider;
import eu.lukasschoerghuber.publictransporthero.telegram.intenthandler.IntentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Component
public class PublicTransportHeroTelegramBot extends TelegramLongPollingBot {
    private static final String HANDLER_POSTFIX = "Handler";

    private final String apiToken;
    private final String botUsername;

    private final ChatbotContextProvider contextProvider;

    public PublicTransportHeroTelegramBot(
            @Value("${telegram.bot.apiToken}") String apiToken,
            @Value("${telegram.bot.username}") String botUsername,
            @Autowired ChatbotContextProvider contextProvider
    ) {
        this.apiToken = apiToken;
        this.botUsername = botUsername;
        this.contextProvider = contextProvider;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if ((message != null) && (message.hasText())) {
            Integer userId = message.getFrom().getId();
            ChatbotContext context = this.contextProvider.fetchOrCreateContext(userId);

            var intentName = "HelloIntent";
            try {
                Class<? extends IntentHandler> intentHandlerClass =
                        (Class<? extends IntentHandler>) Class.forName(
                                IntentHandler.class.getPackageName()
                                + "."
                                + intentName
                                + HANDLER_POSTFIX
                        );
                var intentHandlerConstructor = Arrays.stream(intentHandlerClass.getDeclaredConstructors())
                        .filter(constructor -> constructor.getParameterCount() == 0)
                        .findFirst()
                        .orElseThrow();
                IntentHandler intentHandler = (IntentHandler) intentHandlerConstructor.newInstance();
                var replyMessage = intentHandler.handleIntent(message, context);
                this.execute(replyMessage);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                // TODO: log error
                System.err.printf("Could not find handler for intent '%s'%n", intentName);
                e.printStackTrace();
            } catch (TelegramApiException exception) {
                // TODO: log error
                exception.printStackTrace();
            } finally {
                this.contextProvider.persistContext(context);
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
