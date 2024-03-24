package edu.java.domain.jpa;

import edu.java.domain.jpa.entity.LinksEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaLinkRepository extends JpaRepository<LinksEntity, Long> {
    @Query("select l from LinksEntity l where l.uri = ?1")
    Optional<LinksEntity> findByUri(String uri);
}
