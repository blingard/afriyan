package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ThemeTraiterDTO;
import org.ligot.afriyan.entities.ThemeTraiter;
import org.ligot.afriyan.mapper.ThemeTraiterMapper;
import org.ligot.afriyan.repository.IThemeTraiterRepository;
import org.ligot.afriyan.service.IThemeTraiter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ThemeTraiterImpl implements IThemeTraiter {

    private final IThemeTraiterRepository repository;
    private final ThemeTraiterMapper mapper;

    public ThemeTraiterImpl(IThemeTraiterRepository repository, ThemeTraiterMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ThemeTraiterDTO save(ThemeTraiterDTO themeTraiterDTO) {
        return mapper.toDTO(repository.save(mapper.create(themeTraiterDTO)));
    }

    @Override
    public List<ThemeTraiterDTO> getListActive() {
        return repository.findThemeTraiterByActiveIsTrue().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<ThemeTraiterDTO> getList() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public ThemeTraiterDTO findById(Long id) throws Exception {
        return mapper.toDTO(repository.findById(id).orElse(null));
    }

    @Override
    public ThemeTraiterDTO update(ThemeTraiterDTO themeTraiterDTO, Long id) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {
        ThemeTraiter themeTraiter = repository.findById(id).orElse(null);
        if(themeTraiter!=null){
            repository.delete(themeTraiter);
        }

    }

    @Override
    public void active(Long id) {
        ThemeTraiter themeTraiter = repository.findById(id).orElse(null);
        if(themeTraiter!=null){
            themeTraiter.setActive(!themeTraiter.isActive());
            repository.save(themeTraiter);
        }
    }
}
