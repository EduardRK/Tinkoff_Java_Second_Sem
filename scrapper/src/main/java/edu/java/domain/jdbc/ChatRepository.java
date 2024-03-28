package edu.java.domain.jdbc;

public interface ChatRepository {
    void registerChat(long tgChatId);

    void deleteChat(long tgChatId);

    boolean chatRegistered(long tgChatId);

    boolean correctChatId(long tgChatId);
}
