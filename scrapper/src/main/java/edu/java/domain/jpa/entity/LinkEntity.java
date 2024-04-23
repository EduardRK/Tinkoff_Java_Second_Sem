package edu.java.domain.jpa.entity;

import edu.java.domain.dto.Link;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "link")
public class LinkEntity {
    @ManyToMany(mappedBy = "links", fetch = FetchType.EAGER)
    Set<ChatEntity> chats = new HashSet<>();
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

    public LinkEntity() {
    }

    public LinkEntity(
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

    public LinkEntity(
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

    public Set<ChatEntity> chats() {
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
