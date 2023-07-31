package org.ligot.afriyan.init;

import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.Administrateur;
import org.ligot.afriyan.entities.Groupes;
import org.ligot.afriyan.entities.Roles;
import org.ligot.afriyan.repository.IGroupesRepository;
import org.ligot.afriyan.repository.IRolesRepository;
import org.ligot.afriyan.service.IAdministrateur;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;

@Component
public class Database implements CommandLineRunner {
    private final IGroupesRepository serviceGroupe;
    private final IRolesRepository serviceRole;
    private final IAdministrateur serviceAdmin;
    @Autowired
    private IUtilisateur iUtilisateur;

    public Database(IGroupesRepository serviceGroupe, IRolesRepository serviceRole, IAdministrateur serviceAdmin) {
        this.serviceGroupe = serviceGroupe;
        this.serviceRole = serviceRole;
        this.serviceAdmin = serviceAdmin;
    }


    @Override
    public void run(String... args) throws Exception {
        try{
            Roles roles1 =  serviceRole.save(new Roles(null, RolesName.SUPERADMIN.toString(),"Super utilisateur","all"));
            Roles roles2 = serviceRole.save(new Roles(null, RolesName.ADMIN.toString(),"Admin","all"));
            Roles roles3 = serviceRole.save(new Roles(null, RolesName.ROOT.toString(),"Utilisateur Root","all"));
            Roles roles4 = serviceRole.save(new Roles(null, RolesName.USER.toString(),"Utilisateur","all"));
            Roles roles5 = serviceRole.save(new Roles(null, RolesName.VISITOR.toString(),"Visiteur","all"));
            UtilisateurDTO dto = new UtilisateurDTO();
            dto.setEmail("dsfd@sdf.sdf");
            dto.setNumero_telephone("1236547789");
            dto.setNom("sdfsdfdsfsd");
            dto.setPrenom("sdfsdfsdf");

            if(serviceAdmin.codeExist("000000000000000")){
                Administrateur administrateur = new Administrateur();
                administrateur.getRoles().add(roles1);
                administrateur.setCode("000000000000000");
                administrateur.setNom("Administrateur");
                administrateur.setPrenom("Administrateur");
                administrateur.setEmail("root@test.com");
                administrateur.setPwd("1234");
                administrateur.setdCreation(new Date());
                administrateur.getRoles().add(roles1);
                serviceAdmin.save(administrateur);
            }
            Groupes groupes = new Groupes(
                    null,
                    new HashSet<>(),
                    RolesName.USER.toString(),
                    "Groupe des utilisateurs",
                    "Description",
                    new HashSet<>());
            groupes.getRoles().add(roles4);
            serviceGroupe.save(groupes);
            iUtilisateur.save(dto,1L);
            Groupes groupes1 = new Groupes(
                    null,
                    new HashSet<>(),
                    RolesName.ADMIN.toString(),
                    "Groupe des administrateurs",
                    "Description",
                    new HashSet<>());
            groupes1.getRoles().add(roles2);
            serviceGroupe.save(groupes1);
        }catch (Exception e){}






    }
}
