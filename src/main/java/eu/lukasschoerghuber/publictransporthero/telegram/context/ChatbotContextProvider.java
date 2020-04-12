package eu.lukasschoerghuber.publictransporthero.telegram.context;

public interface ChatbotContextProvider {
    ChatbotContext fetchOrCreateContext(Integer userId);
    ChatbotContext persistContext(ChatbotContext context);
}
