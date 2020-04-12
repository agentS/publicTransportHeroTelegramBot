package eu.lukasschoerghuber.publictransporthero.telegram.context;

public final class ChatbotContext {
    private final Integer userID;

    public ChatbotContext(Integer userID) {
        this.userID = userID;
    }

    public Integer getUserID() {
        return this.userID;
    }
}
