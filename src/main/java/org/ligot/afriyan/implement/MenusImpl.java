package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.MenusDTO;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.entities.Categories;
import org.ligot.afriyan.entities.FrontType;
import org.ligot.afriyan.entities.Menus;
import org.ligot.afriyan.repository.IMenusRepository;
import org.ligot.afriyan.service.IMenus;
import org.ligot.afriyan.sondage.mapper.MenusMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenusImpl implements IMenus {

    private final IMenusRepository repository;
    private final MenusMapper mapper;

    public MenusImpl(IMenusRepository repository, MenusMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(MenusDTO menusDTO) {
        if(repository.findByNameAndFrontType(menusDTO.getName(), menusDTO.getFrontType()).isPresent())
            throw new RuntimeException("Le menu "+menusDTO.getName()+" du front "+menusDTO.getFrontType()+" existe deja");
        Optional<Menus> rootMenusOpt = repository.findByNameAndFrontType("/", menusDTO.getFrontType());
        if(rootMenusOpt.isEmpty()){
            Menus root = repository.save(new Menus(null, "/", "Root Path", true, menusDTO.getFrontType(), new HashSet<>(), null));
            rootMenusOpt = Optional.of(root);
        }
        Menus menus = mapper.toEntity(menusDTO);
        menus.setId(null);
        menus.setRootMenu(rootMenusOpt.get());
        menus = repository.save(menus);
        Menus root = rootMenusOpt.get();
        root.getSubMenus().add(menus);
        repository.save(root);

    }

    @Override
    public void init(Categories categories) {
        for(FrontType frontType : FrontType.values()){
            try {
                save(new MenusDTO(null, categories.getCode(), categories.getDescription(), categories.isStatus(), frontType, new HashSet<>(), null));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public PageDTO<MenusDTO> getAllRootMenu(int page) {
        page=page-1;
        if(page<0)
            page = 0;
        Page<Menus> menusPage = repository.findAllByRootMenu(null,PageRequest.of(page, 10));
        return new PageDTO<MenusDTO>(
                new PageImpl<>(
                        menusPage.stream().map(mapper::toDTO).collect(Collectors.toList()),
                        menusPage.getPageable(),
                        menusPage.getTotalElements()
                )
        );
    }

    @Override
    public MenusDTO listAllMenuActivePath(FrontType frontType) {
        Menus menus = repository.findRootMenu(frontType, true).orElseThrow(()->new RuntimeException("Menu non trouve"));
        return mapper.toDTO(menus);
    }



    private UUID convertToUUID(String id){
        try{
            return UUID.fromString(id);
        }catch (Exception ex){
            throw new RuntimeException("L'identifiant saisi n'est pas valide");
        }
    }

    @Override
    public MenusDTO findById(String id) {
        UUID uuid = convertToUUID(id);
        Menus menus = repository.findById(uuid).orElseThrow(()->new RuntimeException("Menu non trouve"));
        return mapper.toDTO(menus);
    }

    @Override
    public void update(String code, String description, boolean status) {
        List<Menus> menusList = repository.findByName(code);
        menusList.forEach(menus -> {
            menus.setDescription(description);
            menus.setStatus(status);
            repository.save(menus);
        });
    }
}
