package edu.java.domain.jpa;

import edu.java.domain.jpa.entity.LinkEntity;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    @Query("select l from LinkEntity l where l.uri = ?1")
    Optional<LinkEntity> findByUri(String uri);

    List<LinkEntity> findByLastCheckLessThan(OffsetDateTime lastCheck);

    @Transactional
    @Modifying
    @Query("update LinkEntity l set l.lastCheck = ?1 where l.lastCheck < ?2")
    void updateLastCheckByLastCheckLessThan(OffsetDateTime lastCheck, OffsetDateTime lastCheck1);

    @Override
    @NotNull
    Optional<LinkEntity> findById(@NotNull Long aLong);

    @Transactional
    @Modifying
    @Query("update LinkEntity l set l.lastUpdate = ?1 where l.id = ?2")
    void updateLastUpdateById(OffsetDateTime lastUpdate, long id);

    @Transactional
    @Modifying
    @Query("update LinkEntity l set l.lastUpdate = ?1")
    void updateLastUpdateBy(OffsetDateTime lastUpdate);

    long deleteByUri(String uri);

    boolean existsByUri(String uri);
}
