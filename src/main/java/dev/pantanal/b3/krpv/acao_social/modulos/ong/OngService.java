package dev.pantanal.b3.krpv.acao_social.modulos.ong;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngPredicates;
import java.util.UUID;

@Service
public class OngService {
    @Autowired
    private OngRepository ongRepository;

    public OngEntity create(OngCreateDto dataRequest) {
        OngEntity entity = new OngEntity();
        entity.setName(dataRequest.name());
        entity.setCnpj(dataRequest.cnpj());
//        entity.setManagerId(dataRequest.managerId());
        OngEntity savedObj = ongRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        ongRepository.delete(id);
    }

    public Page<OngEntity> findAll(Pageable pageable, OngParamsDto filters) {
        BooleanExpression predicate = OngPredicates.buildPredicate(filters);
        Page<OngEntity> objects = ongRepository.findAll(pageable, predicate);
        // lançar exceções
        return objects;
    }

    public OngEntity findById(UUID id) {
        OngEntity obj = ongRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public OngEntity update(UUID ongId, OngUpdateDto request) {
        OngEntity obj = ongRepository.findById(ongId);
        if (request.name() != null){
            obj.setName(request.name());
        }
        if (request.cnpj() != null){
            obj.setCnpj(request.cnpj());
        }
//        if (request.managerId() != null){
//            obj.setManagerId(request.managerId());
//        }
        OngEntity updatedObj = ongRepository.update(obj);
        return updatedObj;
    }


}
