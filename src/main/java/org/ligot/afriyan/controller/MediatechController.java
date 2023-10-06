package org.ligot.afriyan.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.Dto.MediatechDTO;
import org.ligot.afriyan.entities.UserConnect;
import org.ligot.afriyan.repository.IUserConnect;
import org.ligot.afriyan.service.IMediatech;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/mediatech")
public class MediatechController {
    private final IMediatech mediatech;
    private final IUserConnect iUserConnect;

    public MediatechController(IMediatech mediatech, IUserConnect iUserConnect) {
        this.mediatech = mediatech;
        this.iUserConnect = iUserConnect;
    }
    @GetMapping("active")
    public List<MediatechDTO> findAllActive(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        iUserConnect.save(new UserConnect(
                Date.from(Instant.now()),
                userDetails.getUsername()));
        return mediatech.findAllActive();
    }
    @GetMapping
    public List<MediatechDTO> findAll(){
        return mediatech.findAll();
    }

    @PutMapping("active/{id}")
    @RolesAllowed(value = {"SUPERADMIN"})
    public void active(@PathVariable Long id) throws Exception {
        mediatech.activeOrDesable(id);
    }
}
