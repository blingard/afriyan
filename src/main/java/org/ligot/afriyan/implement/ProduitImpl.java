package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ProduitDTO;
import org.ligot.afriyan.entities.Produit;
import org.ligot.afriyan.mapper.ProduitMapper;
import org.ligot.afriyan.repository.IProduitRepository;
import org.ligot.afriyan.service.IProduit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProduitImpl implements IProduit {
    private final ProduitMapper mapper;
    private final IProduitRepository repository;
    private final int PAGE_SIZE = 15;

    public ProduitImpl(ProduitMapper mapper, IProduitRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ProduitDTO findById(Long id) throws Exception {
        Produit produit = repository.findById(id).orElse(null);
        if(produit == null){
            throw new Exception("Le Produit que vous souhaitez modifier n'existes pas");
        }
        return mapper.toDTO(produit);
    }

    @Override
    public ProduitDTO save(ProduitDTO produitDTO) throws Exception {
        return mapper.toDTO(repository.save(mapper.create(produitDTO)));
    }

    @Override
    public Page<ProduitDTO> list(int page) throws Exception {
        Page<Produit> pages = repository.findAll(PageRequest.of(page,PAGE_SIZE));
        return new PageImpl<>(pages.map(mapper::toDTO).toList(),PageRequest.of(page,PAGE_SIZE),pages.getTotalElements());
    }

    @Override
    public ProduitDTO update(ProduitDTO produitDTO, Long id) throws Exception {
        Produit produit = repository.findById(id).orElse(null);
        if(produit == null){
            throw new Exception("Le Produit que vous souhaitez modifier n'existes pas");
        }
        produitDTO.setId(id);
        return mapper.toDTO(repository.save(mapper.create(produitDTO)));
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }
}
