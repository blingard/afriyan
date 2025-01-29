package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.RendezVousDTO;
import org.ligot.afriyan.service.IRendezVous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rendezvous")
public class RendezVousController {

    @Autowired
    IRendezVous rendezVous;

    @PostMapping(value = "/save")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT","GESTIONNAIRECENTRE","USER"})
    RendezVousDTO saveRendezVous(@RequestBody RendezVousDTO rendezVousDto) throws Exception {
        return rendezVous.save(rendezVousDto);
    }

    @PutMapping(value = "/update/{id}")
    @RolesAllowed(value = {"USER"})
    RendezVousDTO updateRendezVous(@RequestBody RendezVousDTO rendezVousDto, @PathVariable Long id) throws Exception {
        return rendezVous.update(rendezVousDto, id);
    }

    @PutMapping(value = "/update/admin/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ROOT","GESTIONNAIRECENTRE"})
    RendezVousDTO updateRendezVousAdmin(@RequestBody RendezVousDTO rendezVousDto, @PathVariable Long id) throws Exception {
        return rendezVous.updateAdmin(rendezVousDto, id);
    }

    @PutMapping(value = "/update/annuller/{idRdv}/{idUser}")
    @RolesAllowed(value = {"USER"})
    void updateRendezVousAnnulle(@PathVariable("idRdv") Long idRdv, @PathVariable("idUser") Long idUser) throws Exception {
        rendezVous.annuller(idRdv, idUser);
    }

    @GetMapping(value = "/list/{page}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT","GESTIONNAIRECENTRE","USER"})
    Page<RendezVousDTO> listRendezVous(@PathVariable  int page) throws Exception {
        return rendezVous.list(page);
    }

    @DeleteMapping(value = "/delete/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT","GESTIONNAIRECENTRE","USER"})
    void deleteRendezVous (@PathVariable long id) throws Exception{
        rendezVous.delete(id);
    }

    @GetMapping(value = "/getById/{id}")
    RendezVousDTO listById(@PathVariable Long id) throws Exception {
        return rendezVous.findById(id);
    }

    @GetMapping(value = "/getByuserId/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT"})
    List<RendezVousDTO> findByUserId(@PathVariable Long id) throws Exception {
        return rendezVous.findByUserId(id);
    }

    @GetMapping(value = "/getByCpId/{id}")
    @RolesAllowed(value = {"SUPERADMIN","ADMIN","ROOT","GESTIONNAIRECENTRE"})
    List<RendezVousDTO> findByCPId(@PathVariable Long id) throws Exception {
        return rendezVous.findByCPId(id);
    }


}
