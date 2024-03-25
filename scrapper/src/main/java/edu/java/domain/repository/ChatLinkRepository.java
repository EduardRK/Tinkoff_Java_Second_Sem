package edu.java.domain.repository;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import java.util.List;

public interface ChatLinkRepository {
    void addChatLink(long tgChatId, long linkId);

    void removeChatLink(long tgChatId, long linkId);

    List<Chat> getAllChats(long linkId);

    List<Link> getAllLinks(long tgChatId);

    boolean chatTrackedLink(long tgChatId, String uri);

    void deleteAllTrackLink(long tgChatId);
}
