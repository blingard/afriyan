package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.UserConnect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserConnect extends JpaRepository<UserConnect, Long> {
}
