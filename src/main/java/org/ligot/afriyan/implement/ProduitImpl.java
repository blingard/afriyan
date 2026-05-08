package org.ligot.afriyan.implement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.CurrentTimestamp;
import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.Dto.ProduitRequest;
import org.ligot.afriyan.entities.Produit;
import org.ligot.afriyan.entities.ServiceEntity;
import org.ligot.afriyan.mapper.ProduitMapper;
import org.ligot.afriyan.repository.IProduitRepository;
import org.ligot.afriyan.repository.IServiceEntityRepository;
import org.ligot.afriyan.service.IProduit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class ProduitImpl implements IProduit {
    private final ProduitMapper mapper;
    private final IProduitRepository repository;
    private final IServiceEntityRepository iServiceEntityRepository;
    private final int PAGE_SIZE = 15;

    public ProduitImpl(ProduitMapper mapper, IProduitRepository repository, IServiceEntityRepository iServiceEntityRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.iServiceEntityRepository = iServiceEntityRepository;
    }

    @Override
    public ProduitDTO findById(Long id) throws Exception {
        Produit produit = repository.findById(id).orElseThrow(()->new Exception("Le Produit que vous souhaitez modifier n'existes pas"));
        return mapper.toDTO(produit);
    }

    @Override
    public ProduitDTO save(ProduitRequest produitRequest) throws Exception {
        ServiceEntity serviceEntity = iServiceEntityRepository.findById(produitRequest.serviceId()).orElseThrow(()->new RuntimeException("Service not found"));
        Produit produit = new Produit();
        produit.setNom(produitRequest.libelle());
        produit.setLibelle(produitRequest.libelle());
        produit.setDescription(produitRequest.description());
        produit.setPrix(produitRequest.prix());
        produit.setDatCreation(Date.from(Instant.now()));
        produit.setService(serviceEntity);
        produit = repository.save(produit);
        Set<Produit> produits = new HashSet<>();
        if(serviceEntity.getProduits() == null){
            produits.add(produit);
        }else {
            produits = serviceEntity.getProduits();
            produits.add(produit);
        }
        serviceEntity.setProduits(produits);
        iServiceEntityRepository.save(serviceEntity);

        return mapper.toDTO(produit);
    }

    @Override
    public Page<ProduitDTO> list(int page) throws Exception {
        Page<Produit> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public ProduitDTO update(ProduitDTO produitDTO, Long id) throws Exception {
        Produit produit = repository.findById(id).orElseThrow(()->new Exception("Le Produit que vous souhaitez modifier n'existes pas"));
        if(!Objects.equals(produitDTO.getId(), id))
            throw new RuntimeException("Information non concordante");
        mapper.update(produitDTO, produit);

        return mapper.toDTO(repository.save(produit));
    }

    @Override
    public void delete(Long id) throws Exception {
        Produit produit = repository.findById(id).orElseThrow(()->new Exception("Le Produit que vous souhaitez modifier n'existes pas"));
        produit.setActive(!produit.isActive());
        repository.save(produit);
    }
}
