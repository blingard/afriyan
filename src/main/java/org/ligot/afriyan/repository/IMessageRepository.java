package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMessageRepository extends JpaRepository<Message, Long> {
}
