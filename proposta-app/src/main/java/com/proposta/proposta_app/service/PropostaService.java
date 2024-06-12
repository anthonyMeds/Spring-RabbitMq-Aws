package com.proposta.proposta_app.service;

import com.proposta.proposta_app.dto.PropostaRequestDto;
import com.proposta.proposta_app.dto.PropostaResponseDto;
import com.proposta.proposta_app.entity.Proposta;
import com.proposta.proposta_app.mapper.PropostaMapper;
import com.proposta.proposta_app.repository.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@AllArgsConstructor
@Service
public class PropostaService {

    PropostaRepository propostaRepository;

    NotificacaoService notificacaoService;

    public PropostaResponseDto criar (@RequestBody PropostaRequestDto requestDto){

        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDto);

        propostaRepository.save(proposta);

        PropostaResponseDto propostaResponseDto = PropostaMapper.INSTANCE.convertEntityToDto(proposta);

        notificacaoService.notificar(propostaResponseDto, "proposta-pendente.ex");

        return propostaResponseDto;
    }

    public List<PropostaResponseDto> obterPropostas() {

        Iterable<Proposta> propostas = propostaRepository.findAll();

        return PropostaMapper.INSTANCE.convertEntityListToDtoList(propostas);

    }
}
