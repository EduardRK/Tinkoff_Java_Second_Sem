package edu.java.service.jpa;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.domain.jpa.JpaChatRepository;
import edu.java.domain.jpa.JpaLinkRepository;
import edu.java.domain.jpa.entity.ChatEntity;
import edu.java.domain.jpa.entity.LinkEntity;
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
import edu.java.service.ScrapperService;
import jakarta.persistence.EntityManager;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class JpaScrapperService implements ScrapperService {
    private final JpaChatRepository chatRepository;
    private final JpaLinkRepository linkRepository;
    private final EntityManager entityManager;

    public JpaScrapperService(
        JpaLinkRepository linkRepository,
        JpaChatRepository chatRepository,
        EntityManager entityManager
    ) {
        this.chatRepository = chatRepository;
        this.linkRepository = linkRepository;
        this.entityManager = entityManager;
    }

    @Override
    public void registerChat(long tgChatId) throws BadRequestException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatRepository.findById(tgChatId).isPresent()) {
            throw new ChatAlreadyRegisteredException(tgChatId);
        }

        chatRepository.save(new ChatEntity(tgChatId));
        entityManager.flush();
    }

    @Override
    public void deleteChat(long tgChatId) throws BadRequestException, NotFoundException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatRepository.findById(tgChatId).isEmpty()) {
            throw new ChatNotRegisteredException(tgChatId);
        }

        ChatEntity chatEntity = chatRepository.findById(tgChatId).get();
        chatEntity.links().clear();
        chatRepository.deleteById(tgChatId);

        entityManager.flush();
    }

    @Override
    public LinkResponse add(long tgChatId, String uri) throws BadRequestException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatRepository.findById(tgChatId).isEmpty()) {
            throw new ChatsNotRegisteredException(List.of(tgChatId), uri);
        } else if (
            chatRepository.findById(tgChatId)
                .get()
                .links()
                .stream()
                .map(LinkEntity::uri)
                .toList()
                .contains(uri)
        ) {
            throw new UriAlreadyTrackedException(List.of(tgChatId), uri);
        }

        ChatEntity chatEntity = chatRepository.findById(tgChatId).get();

        Optional<LinkEntity> existingLink = linkRepository.findByUri(uri);
        LinkEntity linkEntity;

        if (existingLink.isPresent()) {
            linkEntity = existingLink.get();
            linkEntity.setLastCheck(OffsetDateTime.now());
            linkRepository.save(linkEntity);
        } else {
            linkEntity = new LinkEntity(uri, OffsetDateTime.now(), OffsetDateTime.now());
            linkRepository.save(linkEntity);
        }

        chatEntity.addLink(linkEntity);
        chatRepository.save(chatEntity);

        return new LinkResponse(linkEntity.id(), uri);
    }

    @Override
    public LinkResponse remove(long tgChatId, String uri) throws BadRequestException, NotFoundException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        } else if (chatRepository.findById(tgChatId).isEmpty()) {
            throw new ChatsNotRegisteredException(List.of(tgChatId), uri);
        } else if (
            !chatRepository.findById(tgChatId)
                .get()
                .links()
                .stream()
                .map(LinkEntity::uri)
                .toList()
                .contains(uri)
        ) {
            throw new ChatNotTrackedUriException(tgChatId, uri);
        }

        LinkEntity linkEntity = linkRepository.findByUri(uri).get();
        linkRepository.deleteByUri(uri);
        chatRepository.findById(tgChatId).get().links().remove(linkEntity);

        return new LinkResponse(tgChatId, uri);
    }

    @Override
    public ListLinksResponse listAll(long tgChatId) throws BadRequestException {
        if (notCorrectChatId(tgChatId)) {
            throw new IncorrectDataException(tgChatId);
        }

        ChatEntity chatEntity = chatRepository.findById(tgChatId)
            .orElseThrow(
                () -> new ChatsNotRegisteredException(List.of(tgChatId), "")
            );

        List<Link> linkList = chatEntity.links().stream().map(LinkEntity::link).toList();

        return new ListLinksResponse(
            linkList.stream()
                .map(link -> new LinkResponse(link.id(), link.uri()))
                .toList(),
            linkList.size()
        );
    }

    @Override
    public List<Link> findAllWithFilter(Duration updateCheckTime) {
        OffsetDateTime minuses = OffsetDateTime.now().minus(updateCheckTime);

        List<Link> list = linkRepository.findByLastCheckLessThan(minuses)
            .stream()
            .map(LinkEntity::link)
            .toList();

        linkRepository.updateLastCheckByLastCheckLessThan(OffsetDateTime.now(), minuses);

        return list;
    }

    @Override
    public void updateLastUpdateTime(Link link) {
        linkRepository.updateLastUpdateById(link.lastUpdate(), link.id());
    }

    @Override
    public List<Chat> getAllChats(long linkId) {
        return linkRepository.findById(linkId)
            .orElse(new LinkEntity())
            .chats()
            .stream()
            .map(ChatEntity::chat)
            .toList();
    }

    @Override
    public void updateAllLastUpdateTime() {
        linkRepository.updateLastUpdateBy(OffsetDateTime.now());
    }

    @Override
    public List<Link> getAllLinks(long chatId) {
        return chatRepository.findById(chatId)
            .orElse(new ChatEntity())
            .links()
            .stream()
            .map(LinkEntity::link)
            .toList();
    }

    private boolean notCorrectChatId(long tgChatId) {
        return tgChatId <= 0;
    }

}
