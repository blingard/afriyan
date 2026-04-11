package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Constantes;
import org.ligot.afriyan.Dto.ArticlesDTO;
import org.ligot.afriyan.Dto.UtilisateurDTO;
import org.ligot.afriyan.entities.*;
import org.ligot.afriyan.mapper.ArticlesMapper;
import org.ligot.afriyan.mapper.UtilisateurMapper;
import org.ligot.afriyan.repository.IArticlesRepository;
import org.ligot.afriyan.repository.IUserConnect;
import org.ligot.afriyan.service.IArticles;
import org.ligot.afriyan.service.ICategories;
import org.ligot.afriyan.service.IUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticlesImpl implements IArticles {
    private final IArticlesRepository repository;
    private final ArticlesMapper mapper;
    private final IUserConnect iUserConnect;
    private final ICategories iCategories;
    private final IUtilisateur utilisateur;
    private final UtilisateurMapper utilisateurMapper;
    private final UtilsService utilsService;

    private final FileStorageService fileStorageService;

    public ArticlesImpl(IArticlesRepository repository, ArticlesMapper mapper, IUserConnect iUserConnect,
                        ICategories iCategories, IUtilisateur utilisateur, UtilisateurMapper utilisateurMapper,
                        UtilsService utilsService, FileStorageService fileStorageService) {
        this.repository = repository;
        this.mapper = mapper;
        this.iUserConnect = iUserConnect;
        this.iCategories = iCategories;
        this.utilisateur = utilisateur;
        this.utilisateurMapper = utilisateurMapper;
        this.utilsService = utilsService;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public ArticlesDTO save(MultipartFile file, ArticlesDTO articlesDTO) throws Exception {
        getUser();
        Categories categories = iCategories.findCategoriesById(articlesDTO.getCategories().getId());
        if (Objects.equals(categories.isStatus(), Boolean.FALSE.booleanValue()))
            throw new RuntimeException(
                    "La categorie selectionner n'est pas active. Veillez l'active avant de l'itilise");
        String name = fileStorageService.storeParagraphFileImage(file, Constantes.ARTICLEIMAGESUBPATH);
        Articles articles = mapper.create(articlesDTO);
        articles.setDate(new Date());
        articles.setStatus(false);
        articles.setPhote(name);
        articles.setCategories(categories);
        return mapper.toDTO(repository.save(articles));
    }

    @Override
    public ArticlesDTO save(ArticlesDTO articlesDTO) throws Exception {
        Categories categories = iCategories.findCategoriesById(articlesDTO.getCategories().getId());
        if (Objects.equals(categories.isStatus(), Boolean.FALSE.booleanValue()))
            throw new RuntimeException(
                    "La categorie selectionner n'est pas active. Veillez l'active avant de l'itilise");
        Articles articles = mapper.create(articlesDTO);
        articles.setDate(new Date());
        articles.setStatus(false);
        articles.setCategories(categories);
        return mapper.toDTO(repository.save(articles));
    }

    private Utilisateur getUser() throws Exception {
        return utilsService.getUser();
    }

    private ArticlesDTO findWithFile(Articles articles) {
        ArticlesDTO articlesDTO = mapper.toDTO(articles);
        return articlesDTO;
    }

    private ArticlesDTO findWithFile(Articles articles, TypeDonne typeDonne) {
        if (Objects.equals(typeDonne, TypeDonne.ARTICLE)) {
            return findWithFile(articles);
        } else {
            return mapper.toDTO(articles);
        }
    }

    @Override
    public List<ArticlesDTO> getList(TypeDonne typeDonne) {
        return repository.findAllByTypeDonne(typeDonne).stream().map(this::findWithFile).collect(Collectors.toList());
    }

    @Override
    public List<ArticlesDTO> getList(TypeDonne typeDonne, String menuId) {
        Categories categories = iCategories.findCategoriesByMenuId(menuId);
        return repository.findAllByTypeDonneAndCategoriesAndStatusTrue(typeDonne, categories).stream()
                .map(this::findWithFile).collect(Collectors.toList());
    }

    @Override
    public List<ArticlesDTO> getListAdmin(TypeDonne typeDonne, String categorieId) {
        Categories categories = iCategories.findCategoriesById(UUID.fromString(categorieId));
        return repository.findAllByTypeDonneAndCategoriesAndStatusTrue(typeDonne, categories).stream()
                .map(this::findWithFile).collect(Collectors.toList());
    }

    @Override
    public List<ArticlesDTO> getListActive(TypeDonne typeDonne) {
        return repository.findAllByStatusTrueAndTypeDonne(typeDonne).stream()
                .map(articles -> this.findWithFile(articles, typeDonne)).toList();
    }

    @Override
    public ArticlesDTO findById(Long id) throws Exception {
        Articles articles = repository.findById(id).orElse(null);
        if (articles == null)
            throw new Exception("data not found");
        return findWithFile(articles);
    }

    @Override
    public ArticlesDTO findByIdActive(Long id) throws Exception {
        Articles articles = repository.findById(id).orElse(null);
        if (articles == null)
            throw new Exception("data not found");
        articles.setLue(articles.getLue() + 1);
        repository.save(articles);
        return findWithFile(articles);
    }

    @Override
    public Page<ArticlesDTO> getPage(int lenght, TypeDonne typeDonne) {
        if (lenght < 0)
            lenght = 0;
        Page<Articles> page = repository.findAllByTypeDonne(typeDonne, PageRequest.of(lenght, 15));
        return new PageImpl<>(
                page.getContent().stream().map(this::findWithFile).collect(Collectors.toList()),
                PageRequest.of(lenght, 15),
                page.getSize());
    }

    @Override
    public ArticlesDTO update(ArticlesDTO articlesDTO, Long id) throws Exception {
        Articles article = repository.findById(id).orElse(null);
        if (article == null) {
            throw new Exception("Le Article que vous souhaitez modifier n'existes pas");
        }
        articlesDTO.setId(id);
        mapper.update(articlesDTO, article);
        return mapper.toDTO(repository.save(article));

    }

    @Override
    public void delete(Long id) {
        Articles articles = repository.findById(id).orElse(null);
        if (articles != null)
            repository.delete(articles);
    }

    @Override
    public List<ArticlesDTO> get6TopDesc(TypeDonne typeDonne) {
        return repository.findTop6ByTypeDonneAndStatusIsTrue(typeDonne, Sort.by("id").descending()).stream()
                .map(this::findWithFile).toList();
    }

    @Override
    public void active(Long id, TypeDonne typeDonne) {
        Articles articles = repository.findById(id).orElse(null);
        if (articles != null && articles.getType().equals(typeDonne)) {
            articles.setStatus(!articles.isStatus());
            repository.save(articles);
        }
    }
}
