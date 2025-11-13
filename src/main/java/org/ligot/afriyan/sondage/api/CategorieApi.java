package org.ligot.afriyan.sondage.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.ligot.afriyan.Dto.CategoriesDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.service.ICategories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "Categorie", description = "Gestion des categorie")
public class CategorieApi {
    private final ICategories service;

    public CategorieApi(ICategories service) {
        this.service = service;
    }

    @GetMapping("/api/categorie")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public ResponseEntity<PageDTO<CategoriesDTO>> listByPage(@RequestParam(name = "page", defaultValue = "0")int page) {
        return ResponseEntity.ok(service.getListAll(page));
    }
    @GetMapping("/api/categorie/list")
    public ResponseEntity<List<CategoriesDTO>> listAllActive() {
        return ResponseEntity.ok(service.listAllActive());
    }
    @PutMapping("/api/categorie/active/{id}")
    public void activeOrDesable(@PathVariable("id") String id) throws Exception{
        service.active(id);
    }

    @GetMapping("/api/categorie/{id}")
    public ResponseEntity<CategoriesDTO> findByIdAnomyne(@PathVariable("id") String id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/api/categorie/save")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void save(@RequestBody @Valid CategoriesDTO categoriesDTO) {
            service.save(categoriesDTO);
    }
    @PutMapping("/api/categorie/update/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    public void update(@PathVariable("id") String id, @RequestBody @Valid CategoriesDTO categoriesDTO) {
        service.update(categoriesDTO, id);
    }

}
