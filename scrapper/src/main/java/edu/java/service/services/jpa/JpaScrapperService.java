package edu.java.service.services.jpa;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.domain.jpa.JpaChatRepository;
import edu.java.domain.jpa.JpaLinkRepository;
import edu.java.domain.jpa.entity.ChatsEntity;
import edu.java.domain.jpa.entity.LinksEntity;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.BadRequestException.ChatAlreadyRegisteredException;
import edu.java.exceptions.BadRequestException.ChatsNotRegisteredException;
import edu.java.exceptions.BadRequestException.IncorrectDataException;
import edu.java.exceptions.BadRequestException.UriAlreadyTrackedException;
import edu.java.exceptions.NotFoundException.ChatNotRegisteredException;
import edu.java.exceptions.NotFoundException.ChatNotTrackedUriException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import edu.java.service.services.ScrapperService;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class JpaScrapperService implements ScrapperService {
    private final JpaChatRepository chatRepository;
    private final JpaLinkRepository linkRepository;

    public JpaScrapperService(JpaLinkRepository linkRepository, JpaChatRepository chatRepository) {
        this.chatRepository = chatRepository;
        this.linkRepository = linkRepository;
    }

    @Override
    public void registerChat(long tgChatId) throws BadRequestException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatRepository.findById(tgChatId).isPresent()) {
            throw new ChatAlreadyRegisteredException(tgChatId);
        }

        chatRepository.save(new ChatsEntity(tgChatId));
    }

    @Override
    public void deleteChat(long tgChatId) throws BadRequestException, NotFoundException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatRepository.findById(tgChatId).isEmpty()) {
            throw new ChatNotRegisteredException(tgChatId);
        }

        ChatsEntity chatsEntity = chatRepository.findById(tgChatId).get();
        chatsEntity.links().clear();
        chatRepository.deleteById(tgChatId);
    }

    @Override
    public LinkResponse add(long tgChatId, String uri) throws BadRequestException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatRepository.findById(tgChatId).isEmpty()) {
            throw new ChatsNotRegisteredException(List.of(tgChatId), uri);
        } else if (linkRepository.findByUri(uri).isPresent()
            || linkRepository.findByUri(uri).get().chats().contains(new ChatsEntity(tgChatId))
        ) {
            throw new UriAlreadyTrackedException(List.of(tgChatId), uri);
        }

        LinksEntity linksEntity = new LinksEntity(uri, OffsetDateTime.now(), OffsetDateTime.now());
        linkRepository.save(linksEntity);
        ChatsEntity chatsEntity = chatRepository.findById(tgChatId).get();
        chatsEntity.addLink(linksEntity);

        return new LinkResponse(linksEntity.id(), uri);
    }

    @Override
    public LinkResponse remove(long tgChatId, String uri) throws BadRequestException, NotFoundException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatRepository.findById(tgChatId).isEmpty()) {
            throw new ChatsNotRegisteredException(List.of(tgChatId), uri);
        } else if (linkRepository.findByUri(uri).isEmpty()
            || chatRepository.findById(tgChatId).get().links().contains(linkRepository.findByUri(uri).get())) {
            throw new ChatNotTrackedUriException(tgChatId, uri);
        }

        LinksEntity linksEntity = linkRepository.findByUri(uri).get();
        chatRepository.findById(tgChatId).get().links().remove(linksEntity);

        return new LinkResponse(tgChatId, uri);
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) throws BadRequestException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        }

        ChatsEntity chatsEntity = chatRepository.findById(tgChatId)
            .orElseThrow(
                () -> new ChatsNotRegisteredException(List.of(tgChatId), "")
            );

        List<Link> linkList = chatsEntity.links().stream().map(LinksEntity::link).toList();

        return new ListLinksResponse(
            linkList.stream()
                .map(link -> new LinkResponse(link.id(), link.uri()))
                .toList(),
            linkList.size()
        );
    }

    @Override
    public List<Link> findAllWithFilter(Duration updateCheckTime) {
        return null;
    }

    @Override
    public void updateLastUpdateTime(Link link) {

    }

    @Override
    public List<Chat> allChats(long linkId) {
        return null;
    }

    private boolean notCorrectChatId(long tgChatId) {
        return tgChatId <= 0;
    }

}
