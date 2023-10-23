package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.entities.Articles;
import org.ligot.afriyan.entities.Categorie;
import org.ligot.afriyan.entities.TypeDonne;
import org.ligot.afriyan.entities.UserConnect;
import org.ligot.afriyan.mapper.ArticlesMapper;
import org.ligot.afriyan.repository.IArticlesRepository;
import org.ligot.afriyan.repository.IUserConnect;
import org.ligot.afriyan.service.IArticles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticlesImpl implements IArticles {
    private final IArticlesRepository repository;
    private final ArticlesMapper mapper;
    private final IUserConnect iUserConnect;

    public ArticlesImpl(IArticlesRepository repository, ArticlesMapper mapper, IUserConnect iUserConnect) {
        this.repository = repository;
        this.mapper = mapper;
        this.iUserConnect = iUserConnect;
    }

    @Override
    public ArticlesDTO save(ArticlesDTO articlesDTO) {
        Articles articles = mapper.create(articlesDTO);
        articles.setDate(new Date());
        articles.setStatus(false);
        return mapper.toDTO(repository.save(articles));
    }

    @Override
    public List<ArticlesDTO> getList(TypeDonne typeDonne) {
        return repository.findAllByTypeDonne(typeDonne).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ArticlesDTO> getList(TypeDonne typeDonne, Categorie categorie) {
        return repository.findAllByTypeDonneAndCategorieAndStatusTrue(typeDonne, categorie).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ArticlesDTO> getListActive(TypeDonne typeDonne) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            iUserConnect.save(new UserConnect(
                    Date.from(Instant.now()),
                    userDetails.getUsername()));
        }catch (Exception ex){
            iUserConnect.save(new UserConnect(
                    Date.from(Instant.now()),
                    "anonymous"));
        }
        return repository.findAllByStatusTrueAndTypeDonne(typeDonne).stream().map(mapper::toDTO).toList();
    }

    @Override
    public ArticlesDTO findById(Long id) throws Exception {
        Articles articles = repository.findById(id).orElse(null);
        if(articles == null)
            throw new Exception("data not found");
        return mapper.toDTO(articles);
    }

    @Override
    public ArticlesDTO findByIdActive(Long id) throws Exception {
        Articles articles = repository.findById(id).orElse(null);
        if(articles == null)
            throw new Exception("data not found");
        articles.setLue(articles.getLue()+1);
        repository.save(articles);
        return mapper.toDTO(articles);
    }

    @Override
    public Page<ArticlesDTO> getPage(int lenght,TypeDonne typeDonne) {
        if(lenght<0)
            lenght = 0;
        Page<Articles> page = repository.findAllByTypeDonne(typeDonne, PageRequest.of(lenght,15));
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
        mapper.update(articlesDTO,article);
        return mapper.toDTO(repository.save(mapper.create(articlesDTO)));

    }

    @Override
    public void delete(Long id) {
        Articles articles = repository.findById(id).orElse(null);
        if(articles != null)
            repository.delete(articles);
    }

    @Override
    public List<ArticlesDTO> get6TopDesc(TypeDonne typeDonne) {
        return repository.findTop6ByTypeDonne(typeDonne,Sort.by("id").descending()).stream().map(mapper::toDTO).toList();
    }

    @Override
    public void active(Long id, TypeDonne typeDonne) {
        Articles articles = repository.findById(id).orElse(null);
        if(articles != null && articles.getType().equals(typeDonne)){
            articles.setStatus(!articles.isStatus());
            repository.save(articles);
        }
    }
}
