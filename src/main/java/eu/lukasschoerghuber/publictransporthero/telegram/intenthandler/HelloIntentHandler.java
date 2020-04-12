package eu.lukasschoerghuber.publictransporthero.telegram.intenthandler;

import eu.lukasschoerghuber.publictransporthero.telegram.context.ChatbotContext;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelloIntentHandler implements IntentHandler {
    @Override
    public BotApiMethod<Message> handleIntent(Message message, ChatbotContext context) {
        var replyMessage = new SendMessage();
        replyMessage.setText("Hello " + message.getFrom().getFirstName());
        replyMessage.setChatId(message.getChatId());
        return replyMessage;
    }
}
