package edu.java.service.services.jdbc_service;

import edu.java.domain.dto.Link;
import edu.java.domain.repository.ChatLinkRepository;
import edu.java.domain.repository.ChatRepository;
import edu.java.domain.repository.LinkRepository;
import edu.java.domain.repository.jdbc.JdbcChatLinkRepository;
import edu.java.domain.repository.jdbc.JdbcChatRepository;
import edu.java.domain.repository.jdbc.JdbcLinkRepository;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.BadRequestException.ChatAlreadyRegisteredException;
import edu.java.exceptions.BadRequestException.IncorrectDataException;
import edu.java.exceptions.BadRequestException.UriAlreadyTrackedException;
import edu.java.exceptions.NotFoundException.ChatNotRegisteredException;
import edu.java.exceptions.NotFoundException.ChatNotTrackedUriException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import edu.java.service.services.ScrapperService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JdbcScrapperService implements ScrapperService {
    private final ChatRepository chatRepository;
    private final LinkRepository linkRepository;
    private final ChatLinkRepository chatLinkRepository;

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
    @Transactional
    public void deleteChat(long tgChatId) throws BadRequestException, NotFoundException {
        if (!chatRepository.correctChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (!chatRepository.chatRegistered(tgChatId)) {
            throw new ChatNotRegisteredException(tgChatId);
        }

        chatRepository.deleteChat(tgChatId);
    }

    @Override
    @Transactional
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
    @Transactional
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

        List<Link> linkList = chatLinkRepository.allLinks(tgChatId);

        return new ListLinksResponse(
            linkList.stream()
                .map(link -> new LinkResponse(link.id(), link.uri().toString()))
                .toList(),
            linkList.size()
        );
    }
}
