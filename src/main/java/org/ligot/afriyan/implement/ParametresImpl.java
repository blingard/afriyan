package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.PageDTO;
import org.ligot.afriyan.Dto.ParametresDto;
import org.ligot.afriyan.entities.ParamTypeEnum;
import org.ligot.afriyan.entities.Parametres;
import org.ligot.afriyan.entities.UserConnect;
import org.ligot.afriyan.mapper.ParametresMapper;
import org.ligot.afriyan.repository.IParametresRepository;
import org.ligot.afriyan.repository.IUserConnect;
import org.ligot.afriyan.service.IParametres;
import org.springframework.data.domain.*;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParametresImpl implements IParametres {
    private final IParametresRepository repository;
    private final ParametresMapper mapper;
    private final IUserConnect iUserConnect;

    public ParametresImpl(IParametresRepository repository, ParametresMapper mapper, IUserConnect iUserConnect) {
        this.repository = repository;
        this.mapper = mapper;
        this.iUserConnect = iUserConnect;
    }

    private void save(ParametresDto parametresDto) {
        Optional<Parametres> parametres = repository.findByParamTypeEnum(parametresDto.getParamTypeEnum());
        if(parametres.isPresent())
            throw new RuntimeException("Item type "+parametres.get().getParamTypeEnum()+" already exist");
        repository.save(mapper.create(parametresDto));
    }

    @Override
    public void update(ParametresDto parametresDto, Long id) throws Exception {
        Optional<Parametres> parametres = repository.findById(id);
        if(parametres.isEmpty())
            throw new Exception("Item type "+parametres.get().getParamTypeEnum()+" not found");
        if(parametres.get().getId() != id)
            throw new Exception("Invalid data");
        Parametres parametresTS = parametres.get();
        mapper.update(parametresDto, parametresTS);
        repository.save(parametresTS);
    }

    @Override
    public void desable(Long id) throws Exception {
        Optional<Parametres> parametres = repository.findById(id);
        if(parametres.isEmpty())
            throw new Exception("Item id "+parametres.get().getId()+" don't exist");
        Parametres parametresTS = parametres.get();
        parametresTS.setStatus(!parametresTS.isStatus());
        repository.save(parametresTS);
    }

    @Override
    @Transactional(Transactional.TxType.NEVER)
    public void init() throws Exception {
        for (ParamTypeEnum paramTypeEnum: ParamTypeEnum.values()){
            try {
                save(new ParametresDto(null, paramTypeEnum.toString(), "", "", true, paramTypeEnum));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<ParametresDto> findAllActive() {
        return repository
                .findAllByStatusTrueAndParamTypeEnum(ParamTypeEnum.STATISTICS)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParametresDto> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ParametresDto findCall() {
        return mapper.toDTO(repository.findByStatusTrueAndParamTypeEnum(ParamTypeEnum.CALL).orElse(null));
    }

    @Override
    public ParametresDto findLocation() {
        return mapper.toDTO(repository.findByStatusTrueAndParamTypeEnum(ParamTypeEnum.LOCALISATION).orElse(null));
    }

    @Override
    public ParametresDto findSms() {
        return mapper.toDTO(repository.findByStatusTrueAndParamTypeEnum(ParamTypeEnum.SMS).orElse(null));
    }

    @Override
    public ParametresDto findColor(ParamTypeEnum paramTypeEnum) {
        if(paramTypeEnum.toString().contains("COLOR"))
            return mapper.toDTO(repository.findByStatusTrueAndParamTypeEnum(paramTypeEnum).orElse(null));
        return null;
    }

    @Override
    public ParametresDto findWhatsapp() {
        return mapper.toDTO(repository.findByStatusTrueAndParamTypeEnum(ParamTypeEnum.WHATSAPP).orElse(null));
    }

    @Override
    public ParametresDto findFacebook() {
        return mapper.toDTO(repository.findByStatusTrueAndParamTypeEnum(ParamTypeEnum.FACEBOOK).orElse(null));
    }

    @Override
    public ParametresDto findTweeter() {
        return mapper.toDTO(repository.findByStatusTrueAndParamTypeEnum(ParamTypeEnum.TWEETER).orElse(null));
    }

    @Override
    public ParametresDto findYoutube() {
        return mapper.toDTO(repository.findByStatusTrueAndParamTypeEnum(ParamTypeEnum.YOUTUBE).orElse(null));
    }

    @Override
    public Map<String, ParametresDto> findAllLinks() {
        Map<String, ParametresDto> data = new HashMap<>();
        data.put("whatsapp", this.findWhatsapp());
        data.put("tweeter", this.findTweeter());
        data.put("facebook", this.findFacebook());
        data.put("youtube", this.findYoutube());
        data.put("call", this.findCall());
        data.put("sms", this.findSms());
        data.put("location", this.findLocation());
        for(ParamTypeEnum paramTypeEnum : ParamTypeEnum.values()){
            if(paramTypeEnum.toString().contains("COLOR")){
                data.put(paramTypeEnum.toString(), this.findColor(paramTypeEnum));
            }

        }
        return data;
    }

    @Override
    public PageDTO<ParametresDto> find(ParamTypeEnum paramTypeEnum, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        Page<Parametres> parametresPage = repository.findAllByParamTypeEnum(paramTypeEnum, pageable);
        return new PageDTO<>(
                new PageImpl<>(
                        parametresPage.getContent().stream().map(mapper::toDTO).toList(),
                        pageable,
                        parametresPage.getTotalElements()
                )
        );
    }

    @Override
    public ParametresDto findById(Long id) throws Exception {
        Parametres parametres = repository.findById(id).orElseThrow(()->new Exception("Parametre not found"));
        return mapper.toDTO(parametres);
    }

    @Override
    public Long visiteurs() throws Exception {
        return iUserConnect.count();
    }

    @Override
    public PageDTO<ParametresDto> findAllByPage(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        Page<Parametres> parametresPage = this.repository.findAll(pageable);
        return new PageDTO<>(
                new PageImpl<>(
                        parametresPage.getContent().stream().map(mapper::toDTO).toList(),
                        pageable,
                        parametresPage.getTotalElements()
                )
        );
    }
}
