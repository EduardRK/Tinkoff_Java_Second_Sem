package edu.java.service.services;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import java.util.List;

public interface ChatLinkService {
    List<Chat> getAllChats(long linkId);

    List<Link> getAllLinks(long chatId);
}
