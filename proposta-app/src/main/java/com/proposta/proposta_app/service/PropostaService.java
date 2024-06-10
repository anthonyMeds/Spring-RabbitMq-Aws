package com.proposta.proposta_app.service;

import com.proposta.proposta_app.dto.PropostaRequestDto;
import com.proposta.proposta_app.dto.PropostaResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PropostaService {

    public PropostaResponseDto criar (@RequestBody PropostaRequestDto requestDto){
        return null;
    }

}
