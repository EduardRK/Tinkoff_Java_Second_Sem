package edu.java.service.services;

import edu.java.domain.dto.Chat;
import java.util.List;

public interface ChatLinkService {
    List<Chat> allChats(long linkId);
}
