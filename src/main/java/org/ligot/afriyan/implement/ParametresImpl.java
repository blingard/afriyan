package org.ligot.afriyan.implement;

import jakarta.transaction.Transactional;
import org.ligot.afriyan.Dto.ParametresDto;
import org.ligot.afriyan.entities.ParamTypeEnum;
import org.ligot.afriyan.entities.Parametres;
import org.ligot.afriyan.mapper.ParametresMapper;
import org.ligot.afriyan.repository.IParametresRepository;
import org.ligot.afriyan.service.IParametres;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParametresImpl implements IParametres {
    private final IParametresRepository repository;
    private final ParametresMapper mapper;

    public ParametresImpl(IParametresRepository repository, ParametresMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(ParametresDto parametresDto) throws Exception {
        if(parametresDto.getParamTypeEnum().equals(ParamTypeEnum.STATISTICS)){
            repository.save(mapper.create(parametresDto));
        }else{
            Optional<Parametres> parametres = repository.findByStatusTrueAndParamTypeEnum(parametresDto.getParamTypeEnum());
            if(parametres.isPresent())
                throw new Exception("Item type "+parametres.get().getParamTypeEnum()+" already exist");
            repository.save(mapper.create(parametresDto));
        }

    }

    @Override
    public void update(ParametresDto parametresDto, Long id) throws Exception {
        Optional<Parametres> parametres = repository.findById(id);
        if(!parametres.isEmpty())
            throw new Exception("Item type "+parametres.get().getParamTypeEnum()+" already exist");
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
        return data;
    }

    @Override
    public List<ParametresDto> find(ParamTypeEnum paramTypeEnum) {
        return repository.findAllByParamTypeEnum(paramTypeEnum).stream().map(mapper::toDTO).toList();
    }

    @Override
    public ParametresDto findById(Long id) throws Exception {
        Parametres parametres = repository.findById(id).orElseThrow(()->new Exception("Parametre not found"));
        return mapper.toDTO(parametres);
    }
}
