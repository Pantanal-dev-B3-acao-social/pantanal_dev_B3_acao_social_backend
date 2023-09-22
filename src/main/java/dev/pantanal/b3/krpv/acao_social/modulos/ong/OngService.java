package dev.pantanal.b3.krpv.acao_social.modulos.ong;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OngService {
    @Autowired
    private OngRepository ongRepository;
}
