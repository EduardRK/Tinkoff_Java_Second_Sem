package edu.java.domain.jpa.entity;

import edu.java.domain.dto.Link;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Links")
public class LinksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "uri", unique = true)
    private String uri;

    @Column(name = "last_check")
    private OffsetDateTime lastCheck;

    @Column(name = "last_update")
    private OffsetDateTime lastUpdate;

    @ManyToMany(mappedBy = "links")
    Set<ChatsEntity> chats = new HashSet<>();

    public LinksEntity() {
    }

    public LinksEntity(
            long id,
            String uri,
            OffsetDateTime lastCheck,
            OffsetDateTime lastUpdate
    ) {
        this.id = id;
        this.uri = uri;
        this.lastCheck = lastCheck;
        this.lastUpdate = lastUpdate;
    }

    public LinksEntity(
            String uri,
            OffsetDateTime lastCheck,
            OffsetDateTime lastUpdate
    ) {
        this.uri = uri;
        this.lastCheck = lastCheck;
        this.lastUpdate = lastUpdate;
    }

    public long id() {
        return id;
    }

    public String uri() {
        return uri;
    }

    public OffsetDateTime lastCheck() {
        return lastCheck;
    }

    public OffsetDateTime lastUpdate() {
        return lastUpdate;
    }

    public Set<ChatsEntity> chats() {
        return chats;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setLastCheck(OffsetDateTime lastCheck) {
        this.lastCheck = lastCheck;
    }

    public void setLastUpdate(OffsetDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Link link() {
        return new Link(
                id,
                uri,
                lastCheck,
                lastUpdate
        );
    }
}
