package edu.java.domain.jpa.entity;

import edu.java.domain.dto.Chat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "chat")
public class ChatEntity {
    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "chat_link",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    private Set<LinkEntity> links = new HashSet<>();

    public ChatEntity() {

    }

    public ChatEntity(Long id) {
        this.id = id;
    }

    public Long id() {
        return id;
    }

    public Set<LinkEntity> links() {
        return links;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chat chat() {
        return new Chat(id);
    }

    public void addLink(LinkEntity linkEntity) {
        links.add(linkEntity);
        linkEntity.chats().add(this);
    }

    public void removeLink(LinkEntity linkEntity) {
        links.remove(linkEntity);
    }

    public boolean containsLink(String uri) {
        return links
            .parallelStream()
            .map(LinkEntity::uri)
            .collect(Collectors.toSet())
            .contains(uri);
    }
}
