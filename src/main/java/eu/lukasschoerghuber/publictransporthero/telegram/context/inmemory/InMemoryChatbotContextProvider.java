package eu.lukasschoerghuber.publictransporthero.telegram.context.inmemory;

import eu.lukasschoerghuber.publictransporthero.telegram.context.ChatbotContext;
import eu.lukasschoerghuber.publictransporthero.telegram.context.ChatbotContextProvider;
import eu.lukasschoerghuber.publictransporthero.telegram.context.ChatbotContextProviderQualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ChatbotContextProviderQualifier(type = ChatbotContextProviderQualifier.Type.IN_MEMORY)
public class InMemoryChatbotContextProvider implements ChatbotContextProvider {
    private final Map<Integer, ChatbotContext> contextMap;

    public InMemoryChatbotContextProvider() {
        this.contextMap = new HashMap<>();
    }

    @Override
    public ChatbotContext fetchOrCreateContext(Integer userId) {
        if (this.contextMap.containsKey(userId)) {
            return this.contextMap.get(userId);
        } else {
            return new ChatbotContext(userId);
        }
    }

    @Override
    public ChatbotContext persistContext(ChatbotContext context) {
        this.contextMap.put(context.getUserID(), context);
        return context;
    }
}
