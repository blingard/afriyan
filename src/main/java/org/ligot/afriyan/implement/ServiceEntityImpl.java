package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.CentrePartenaireDTO;
import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.Dto.ServiceDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.CentrePartenaire;
import org.ligot.afriyan.entities.Produit;
import org.ligot.afriyan.entities.Status;
import org.ligot.afriyan.mapper.ServiceMapper;
import org.ligot.afriyan.repository.IServiceEntityRepository;
import org.ligot.afriyan.service.IServiceEntity;
import org.ligot.afriyan.entities.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceEntityImpl implements IServiceEntity {
    private final ServiceMapper mapper;
    private final IServiceEntityRepository repository;
    private final int PAGE_SIZE = 15;

    public ServiceEntityImpl(ServiceMapper mapper, IServiceEntityRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ServiceDTO findById(Long id) throws Exception {
        ServiceEntity serviceEntity = repository.findById(id).orElse(null);
        if(serviceEntity == null){
            throw new Exception("Le Service que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(serviceEntity);
    }

    @Override
    public ServiceDTO findByIdUser(Long id) throws Exception {
        try{
            Optional<ServiceEntity> serviceEntity = repository.findServiceEntitiesByIdAndUSRAJActive(id, Status.ACTIVE);
            if(serviceEntity.isEmpty())
                throw new Exception("Le service n'existe pas.");
            if(!filter(serviceEntity.get()))
                throw new Exception("Le service n.a pas de produit valide.");
            return traitement(serviceEntity.get());
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }

    }

    @Override
    public Set<ServiceDTO> findListByIdUser(Long id) throws Exception {
        List<ServiceEntity> serviceEntity = repository.findServiceEntitiesByCentrePartenaire(id, Status.ACTIVE);
        if(serviceEntity.isEmpty())
            throw new Exception("L'USRAJ n'a pas de service avec produit valide.");
        Set<ServiceDTO> serviceDTOS = serviceEntity.stream().filter(this::filter).map(this::traitement).collect(Collectors.toSet());
        if(serviceDTOS.isEmpty())
            throw new Exception("L'USRAJ n'a pas de service avec produit valide.");
        return serviceDTOS;
    }

    private boolean filter(ServiceEntity serviceEntity) {
        Set<ProduitDTO> produitDTOS = new HashSet<>(0);
        if(!serviceEntity.getProduits().isEmpty()){
            produitDTOS = serviceEntity.getProduits().stream().filter(Produit::isActive).map(this::traitement).collect(Collectors.toSet());
        }
        return !produitDTOS.isEmpty();
    }

    private ServiceDTO traitement(ServiceEntity serviceEntity) {
        ServiceDTO dto = new ServiceDTO();
        dto.setLibelle(serviceEntity.getLibelle());
        dto.setId(serviceEntity.getId());
        dto.setDescription(serviceEntity.getDescription());
        Set<ProduitDTO> produitDTOS = new HashSet<>(0);
        dto.setDateCreation(serviceEntity.getDateCreation());
        CentrePartenaireDTO centrePartenaire = new CentrePartenaireDTO();
        centrePartenaire.setId(serviceEntity.getCentrePartenaire().getId());
        centrePartenaire.setNom(serviceEntity.getCentrePartenaire().getNom());
        centrePartenaire.setLibelle(serviceEntity.getCentrePartenaire().getLibelle());
        dto.setCentrePartenaire(centrePartenaire);
        if(!serviceEntity.getProduits().isEmpty()){
            produitDTOS = serviceEntity.getProduits().stream().map(this::traitement).collect(Collectors.toSet());
        }
        dto.setProduits(produitDTOS);
        return dto;
    }

    private ProduitDTO traitement(Produit produit) {
        ProduitDTO dto = new ProduitDTO();
        dto.setNom(produit.getNom());
        dto.setLibelle(produit.getLibelle());
        dto.setId(produit.getId());
        dto.setDescription(produit.getDescription());
        dto.setPrix(produit.getPrix());
        dto.setActive(produit.isActive());
        dto.setDateCreation(produit.getDatCreation());
        return dto;
    }

    @Override
    public ServiceDTO save(ServiceDTO serviceDto) throws Exception {
        return mapper.toDTO(repository.save(mapper.create(serviceDto)));
    }

    @Override
    public Page<ServiceDTO> list(int page) throws Exception {
        Page<ServiceEntity> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public ServiceDTO update(ServiceDTO serviceDto, Long id) throws Exception {
        ServiceEntity serviceEntity = repository.findById(id).orElse(null);
        if(serviceEntity == null){
            throw new Exception("Le Service que vous souhaitez modifier n'existes pas");
        }
        serviceDto.setId(id);
        return mapper.toDTO(repository.save(mapper.create(serviceDto)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public void sendSMS(Set<UtilisateurDTO> utilisateurs, String message) throws Exception {
        System.err.println("Send SMS");
    }

    @Override
    public List<ServiceDTO> listServiceCP(Long id) {
        return repository
                .findServiceEntitiesByCentrePartenaire(
                        new CentrePartenaire(id))
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<ServiceEntity> listServiceCPEntity(Long id) {
        return repository
                .findServiceEntitiesByCentrePartenaire(
                        new CentrePartenaire(id));
    }

    @Override
    public List<ServiceEntity> listActiveServiceCPEntity(Long id) {
        return null;
    }

    @Override
    public List<ServiceDTO> listAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }
}
