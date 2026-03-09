package org.ligot.afriyan.sondage.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.MenusDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.entities.FrontType;
import org.ligot.afriyan.service.IMenus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Menus", description = "Gestion des menus")
public class MenusApi {
    private final IMenus service;

    public MenusApi(IMenus service) {
        this.service = service;
    }

    @GetMapping("/api/menu")
    @RolesAllowed(value = {"GET_MENU"})
    public ResponseEntity<PageDTO<MenusDTO>> listByPage(@RequestParam(name = "page", defaultValue = "0")int page) {
        return ResponseEntity.ok(service.getAllRootMenu(page));
    }
    @GetMapping("/public/api/menu/{frontType}")
    public ResponseEntity<MenusDTO> listAllActive(@PathVariable(value = "frontType", required = true)FrontType frontType) {
        return ResponseEntity.ok(service.listAllMenuActivePath(frontType));
    }
    @GetMapping("/api/menu/find/{id}")
    @RolesAllowed(value = {"GET_MENU"})
    public ResponseEntity<MenusDTO> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findById(id));
    }


}
