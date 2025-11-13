package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.FrontType;
import org.ligot.afriyan.entities.Menus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMenusRepository extends JpaRepository<Menus, UUID> {
    @Query("SELECT DISTINCT m FROM Menus m WHERE m.frontType = :frontType AND m.rootMenu IS NULL ")
    Optional<Menus> findRootMenu(@Param("frontType") FrontType frontType);

    @Query("SELECT DISTINCT m FROM Menus m LEFT JOIN FETCH  m.subMenus sm WHERE m.frontType = :frontType AND m.status = :state  AND m.rootMenu IS NULL AND (sm IS NULL OR sm.status = TRUE)")
    Optional<Menus> findRootMenu(@Param("frontType") FrontType frontType, @Param("state") boolean state);
    Page<Menus> findAllByRootMenu(Menus rootMenu, Pageable pageable);

    Optional<Menus> findByNameAndFrontType(String name, FrontType frontType);
    List<Menus> findByName(String name);
}
