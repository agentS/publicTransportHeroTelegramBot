package eu.lukasschoerghuber.publictransporthero.telegram.intenthandler;

import eu.lukasschoerghuber.publictransporthero.telegram.context.ChatbotContext;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface IntentHandler {
    BotApiMethod<Message> handleIntent(Message message, ChatbotContext context);
}
