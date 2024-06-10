package com.proposta.proposta_app.service;

import com.proposta.proposta_app.dto.PropostaRequestDto;
import com.proposta.proposta_app.dto.PropostaResponseDto;
import com.proposta.proposta_app.entity.Proposta;
import com.proposta.proposta_app.repository.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@Service
public class PropostaService {

    PropostaRepository propostaRepository;

    public PropostaResponseDto criar (@RequestBody PropostaRequestDto requestDto){

        propostaRepository.save(new Proposta());

        return null;
    }

}
