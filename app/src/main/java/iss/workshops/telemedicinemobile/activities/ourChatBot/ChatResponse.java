package iss.workshops.telemedicinemobile.activities.ourChatBot;

import java.util.Objects;

public class ChatResponse {


        String chatBotReply;


    public ChatResponse(String chatBotReply) {
        this.chatBotReply = chatBotReply;
    }

    public String getChatBotReply() {
        return chatBotReply;
    }

    public void setChatBotReply(String chatBotReply) {
        this.chatBotReply = chatBotReply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatResponse that = (ChatResponse) o;
        return Objects.equals(chatBotReply, that.chatBotReply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatBotReply);
    }

    @Override
    public String toString() {
        return "ChatResponse{" +
                "chatBotReply='" + chatBotReply + '\'' +
                '}';
    }
}
