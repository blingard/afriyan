package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.CategoriesDTO;
import org.ligot.afriyan.Dto.MenusDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.entities.Categorie;
import org.ligot.afriyan.entities.Categories;
import org.ligot.afriyan.entities.Menus;
import org.ligot.afriyan.repository.ICategoriesRepository;
import org.ligot.afriyan.service.ICategories;
import org.ligot.afriyan.service.IMenus;
import org.ligot.afriyan.sondage.mapper.CategoriesMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.ligot.afriyan.implement.Utils.genCode;

@Service
@Transactional
public class CategoriesImpl implements ICategories {

    private final ICategoriesRepository repository;
    private final CategoriesMapper mapper;
    private final IMenus menusService;

    public CategoriesImpl(ICategoriesRepository repository, CategoriesMapper mapper, IMenus menusService) {
        this.repository = repository;
        this.mapper = mapper;
        this.menusService = menusService;
    }


    @Override
    public void save(CategoriesDTO categoriesDTO) {
        boolean codeIsCreate = false;
        String code = "";
        while(!codeIsCreate){
            code = genCode("RS",8);
            if(!repository.findByCode(categoriesDTO.getCode()).isPresent())
                codeIsCreate = true;
        }
        categoriesDTO.setCode(code);
        Categories categories = mapper.toEntity(categoriesDTO);
        categories.setId(null);
        categories = repository.save(categories);
        menusService.init(categories);
    }

    @Override
    public void init() {
        for(Categorie categorie : Categorie.values()){
            try {
                save(new CategoriesDTO(null, categorie.name(), categorie.name(), true));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public PageDTO<CategoriesDTO> getListAll(int page) {
        Page<Categories> categoriesPage = repository.findAll(PageRequest.of(page, 10));
        return new PageDTO<CategoriesDTO>(
                new PageImpl<>(
                        categoriesPage.stream().map(mapper::toDTO).collect(Collectors.toList()),
                        categoriesPage.getPageable(),
                        categoriesPage.getTotalElements()
                )
        );
    }

    @Override
    public List<CategoriesDTO> listAllActive() {
        return repository.findByStatus(true).stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<CategoriesDTO> listAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    private UUID convertToUUID(String id){
        try{
            return UUID.fromString(id);
        }catch (Exception ex){
            throw new RuntimeException("L'identifiant saisi n'est pas valide");
        }
    }

    @Override
    public CategoriesDTO findById(String id) {
        UUID uuid = convertToUUID(id);
        Categories categories = repository.findById(uuid).orElseThrow(()->new RuntimeException("Categorie non trouvet"));
        return mapper.toDTO(categories);
    }

    @Override
    public void update(CategoriesDTO categoriesDTO, String id) {
        UUID uuid = convertToUUID(id);
        Categories categories = repository.findById(uuid).orElseThrow(()->new RuntimeException("Categorie non trouvet"));
        if(!Objects.equals(uuid, categoriesDTO.getId()))
            throw new RuntimeException("Information non concordante");
        mapper.update(categoriesDTO, categories);
        repository.save(categories);
        menusService.update(categories.getCode(), categories.getDescription(), categories.isStatus());

    }

    @Override
    public void active(String id) {
        UUID uuid = convertToUUID(id);
        Categories categories = repository.findById(uuid).orElseThrow(()->new RuntimeException("Categorie non trouvet"));
        categories.setStatus(!categories.isStatus());
        repository.save(categories);
        menusService.update(categories.getCode(), categories.getDescription(), categories.isStatus());
    }

    @Override
    public Categories findCategoriesByMenuId(String menuId) {
        try {
            MenusDTO menus = menusService.findById(menuId);
            return repository.findByCode(menus.getName()).orElseThrow(()->new RuntimeException("Categorie non trouve"));
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Categorie non trouve");
        }
    }

    @Override
    public Categories findCategoriesById(UUID id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Categorie non trouve"));
    }
}
