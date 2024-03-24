package edu.java.domain.jpa;

import edu.java.domain.jpa.entity.ChatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<ChatsEntity, Long> {

}
