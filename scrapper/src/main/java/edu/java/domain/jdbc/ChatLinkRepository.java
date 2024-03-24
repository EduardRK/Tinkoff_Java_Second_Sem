package edu.java.domain.jdbc;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import java.util.List;

public interface ChatLinkRepository {
    void addChatLink(long tgChatId, long linkId);

    void removeChatLink(long tgChatId, long linkId);

    List<Chat> allChats(long linkId);

    List<Link> allLinks(long tgChatId);

    boolean chatTrackedLink(long tgChatId, String uri);

    void deleteAllTrackLink(long tgChatId);
}
