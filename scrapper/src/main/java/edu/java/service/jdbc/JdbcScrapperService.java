package edu.java.service.jdbc;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.domain.jdbc.JdbcChatLinkRepository;
import edu.java.domain.jdbc.JdbcChatRepository;
import edu.java.domain.jdbc.JdbcLinkRepository;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.BadRequestException.ChatAlreadyRegisteredException;
import edu.java.exceptions.BadRequestException.IncorrectDataException;
import edu.java.exceptions.BadRequestException.UriAlreadyTrackedException;
import edu.java.exceptions.NotFoundException.ChatNotRegisteredException;
import edu.java.exceptions.NotFoundException.ChatNotTrackedUriException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import edu.java.service.ScrapperService;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class JdbcScrapperService implements ScrapperService {
    private final JdbcChatRepository chatRepository;
    private final JdbcLinkRepository linkRepository;
    private final JdbcChatLinkRepository chatLinkRepository;

    @Autowired
    public JdbcScrapperService(
        JdbcLinkRepository linkRepository,
        JdbcChatRepository chatRepository,
        JdbcChatLinkRepository chatLinkRepository
    ) {
        this.chatLinkRepository = chatLinkRepository;
        this.chatRepository = chatRepository;
        this.linkRepository = linkRepository;
    }

    @Override
    public void registerChat(long tgChatId) throws BadRequestException {
        if (!chatRepository.correctChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatRepository.chatRegistered(tgChatId)) {
            throw new ChatAlreadyRegisteredException(tgChatId);
        }

        chatRepository.registerChat(tgChatId);
    }

    @Override
    public void deleteChat(long tgChatId) throws BadRequestException, NotFoundException {
        if (!chatRepository.correctChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (!chatRepository.chatRegistered(tgChatId)) {
            throw new ChatNotRegisteredException(tgChatId);
        }

        chatLinkRepository.deleteAllTrackLink(tgChatId);
        chatRepository.deleteChat(tgChatId);
    }

    @Override
    public LinkResponse add(long tgChatId, String uri) throws BadRequestException {
        if (!chatRepository.correctChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatLinkRepository.chatTrackedLink(tgChatId, uri)) {
            throw new UriAlreadyTrackedException(List.of(tgChatId), uri);
        }

        long linkId = linkRepository.addLink(Link.link(URI.create(uri)));
        chatLinkRepository.addChatLink(tgChatId, linkId);

        return new LinkResponse(linkId, uri);
    }

    @Override
    public LinkResponse remove(long tgChatId, String uri) throws BadRequestException, NotFoundException {
        if (!chatRepository.correctChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (!chatLinkRepository.chatTrackedLink(tgChatId, uri)) {
            throw new ChatNotTrackedUriException(tgChatId, uri);
        }

        long linkId = linkRepository.removeLink(Link.link(URI.create(uri)));
        chatLinkRepository.removeChatLink(tgChatId, linkId);

        return new LinkResponse(linkId, uri);
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) throws BadRequestException {
        if (!chatRepository.correctChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        }

        List<Link> linkList = chatLinkRepository.getAllLinks(tgChatId);

        return new ListLinksResponse(
            linkList
                .stream()
                .map(link -> new LinkResponse(link.id(), link.uri()))
                .toList(),
            linkList.size()
        );
    }

    @Override
    public List<Link> findAllWithFilter(Duration updateCheckTime) {
        return linkRepository.getAllLinksUpdateLastCheckWithFilter(updateCheckTime);
    }

    @Override
    public void updateLastUpdateTime(Link link, OffsetDateTime now) {
        linkRepository.updateLastUpdateTime(link, now);
    }

    @Override
    public List<Chat> getAllChats(long linkId) {
        return chatLinkRepository.getAllChats(linkId);
    }

    @Override
    public void updateAllLastUpdateTime() {
        linkRepository.updateAllLastUpdateTime(OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public List<Link> getAllLinks(long chatId) {
        return chatLinkRepository.getAllLinks(chatId);
    }
}
