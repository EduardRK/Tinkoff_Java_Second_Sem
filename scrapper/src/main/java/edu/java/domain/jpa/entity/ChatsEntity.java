package edu.java.domain.jpa.entity;

import edu.java.domain.dto.Chat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Chats")
public class ChatsEntity {
    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "ChatLink",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    private Set<LinksEntity> links = new HashSet<>();

    public ChatsEntity() {

    }

    public ChatsEntity(Long id) {
        this.id = id;
    }

    public Long id() {
        return id;
    }

    public Set<LinksEntity> links() {
        return links;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chat chat() {
        return new Chat(id);
    }

}
