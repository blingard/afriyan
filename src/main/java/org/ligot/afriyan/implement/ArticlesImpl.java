package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.entities.Articles;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.mapper.ArticlesMapper;
import org.ligot.afriyan.repository.IArticlesRepository;
import org.ligot.afriyan.service.IArticles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticlesImpl implements IArticles {
    private final IArticlesRepository repository;
    private final ArticlesMapper mapper;

    public ArticlesImpl(IArticlesRepository repository, ArticlesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ArticlesDTO save(ArticlesDTO articlesDTO) {
        return mapper.toDTO(repository.save(mapper.create(articlesDTO)));
    }

    @Override
    public List<ArticlesDTO> getList(TypeDonne typeDonne) {
        return repository.findAllByStatusTrueAndType(typeDonne).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ArticlesDTO findById(Long id) throws Exception {
        Articles articles = repository.findById(id).orElse(null);
        if(articles == null)
            throw new Exception("data not found");
        return mapper.toDTO(articles);
    }

    @Override
    public Page<ArticlesDTO> getPage(int lenght,TypeDonne typeDonne) {
        if(lenght<0)
            lenght = 0;
        Page<Articles> page = repository.findAllByType(typeDonne, PageRequest.of(lenght,15));
        return new PageImpl<>(
                page.getContent().stream().map(mapper::toDTO).collect(Collectors.toList()),
                PageRequest.of(lenght, 15),
                page.getSize()
        );
    }

    @Override
    public ArticlesDTO update(ArticlesDTO articlesDTO, Long id) throws Exception{
        Articles article = repository.findById(id).orElse(null);
        if(article == null){
            throw new Exception("Le Article que vous souhaitez modifier n'existes pas");
        }
        articlesDTO.setId(id);

        return mapper.toDTO(repository.saveAndFlush(mapper.create(articlesDTO)));

    }

    @Override
    public void delete(Long id) {
        Articles articles = repository.findById(id).orElse(null);
        if(articles != null)
            repository.delete(articles);
    }
}
