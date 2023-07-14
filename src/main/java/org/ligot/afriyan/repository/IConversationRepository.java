package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConversationRepository extends JpaRepository<Conversation, Long> {
}
