package org.ligot.afriyan.init;

import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.*;
import org.ligot.afriyan.repository.IGroupesRepository;
import org.ligot.afriyan.repository.IRolesRepository;
import org.ligot.afriyan.service.IAdministrateur;
import org.ligot.afriyan.service.IUtilisateur;
import org.ligot.afriyan.sondage.entities.CategorieEntities;
import org.ligot.afriyan.sondage.repo.CategorieEntitiesRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Database implements CommandLineRunner {
    private final CategorieEntitiesRepo repo;

    private final IGroupesRepository serviceGroupe;
    private final IRolesRepository serviceRole;
    private final IAdministrateur serviceAdmin;
    private final IUtilisateur iUtilisateur;



    public Database(CategorieEntitiesRepo repo, IGroupesRepository serviceGroupe, IRolesRepository serviceRole, IAdministrateur serviceAdmin, IUtilisateur iUtilisateur) {
        this.repo = repo;
        this.serviceGroupe = serviceGroupe;
        this.serviceRole = serviceRole;
        this.serviceAdmin = serviceAdmin;
        this.iUtilisateur = iUtilisateur;
    }

    @Override
    public void run(String... args) throws Exception {
       try{


        }catch (Exception exception){

        }
        try{
           /* for (Categorie categorie : Categorie.values()) {
                if(repo.findByDomain(categorie).isEmpty())
                    repo.save(new CategorieEntities(null, categorie));
            }*/
            /*Roles roles1 =  serviceRole.save(new Roles(null, RolesName.SUPERADMIN.toString(),"Super utilisateur","all"));
            Roles roles2 = serviceRole.save(new Roles(null, RolesName.ADMIN.toString(),"Admin","all"));
            Roles roles3 = serviceRole.save(new Roles(null, RolesName.ROOT.toString(),"Utilisateur Root","all"));
            Roles roles4 = serviceRole.save(new Roles(null, RolesName.USER.toString(),"Utilisateur","all"));
            Roles roles5 = serviceRole.save(new Roles(null, RolesName.VISITOR.toString(),"Visiteur","all"));
            Roles roles6 = serviceRole.save(new Roles(null, RolesName.GESTIONNAIRECENTRE.toString(),"Gestionnaire de centre","all"));
            Groupes groupes2 = new Groupes(
                    null,
                    new HashSet<>(),
                    RolesName.USER.toString(),
                    "Groupe des Utilisateurs",
                    "Description",
                    new HashSet<>());
            groupes2.getRoles().add(serviceRole.findByNom(RolesName.USER.toString()).get());
            serviceGroupe.save(groupes2);*/
            UtilisateurDTO dto = new UtilisateurDTO();
            dto.setEmail("youthfp@youthfp.cm");
            dto.setNumero_telephone("@Youthfp75");
            dto.setNom("Youthfp");
            dto.setPrenom("Inc");
            dto.setStatus(Status.ACTIVE);

            /*if(serviceAdmin.codeExist("000000000000000")){
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
            }*/
            /*Groupes groupes = new Groupes(
                    null,
                    new HashSet<>(),
                    RolesName.SUPERADMIN.toString(),
                    "Groupe des SUPERADMIN",
                    "Description",
                    new HashSet<>());
            groupes.getRoles().add(roles1);
            groupes.getRoles().add(roles2);
            groupes.getRoles().add(roles3);
            groupes.getRoles().add(roles4);
            groupes.getRoles().add(roles5);
            groupes.getRoles().add(roles6);
            groupes = serviceGroupe.save(groupes);*/

            //iUtilisateur.save(dto,153L);
           /* Groupes groupes1 = new Groupes(
                    null,
                    new HashSet<>(),
                    RolesName.ADMIN.toString(),
                    "Groupe des administrateurs",
                    "Description",
                    new HashSet<>());
            groupes1.getRoles().add(roles2);
            serviceGroupe.save(groupes1);*/

        }catch (Exception e){}






    }
}
