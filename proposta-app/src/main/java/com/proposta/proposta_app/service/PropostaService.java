package com.proposta.proposta_app.service;

import com.proposta.proposta_app.dto.PropostaRequestDto;
import com.proposta.proposta_app.dto.PropostaResponseDto;
import com.proposta.proposta_app.entity.Proposta;
import com.proposta.proposta_app.mapper.PropostaMapper;
import com.proposta.proposta_app.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PropostaService {

    private PropostaRepository propostaRepository;

    private NotificacaoService notificacaoService;

    private String exchange;

    public PropostaService(
            PropostaRepository propostaRepository,
            NotificacaoService notificacaoService,
            @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoService = notificacaoService;
        this.exchange = exchange;
    }


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
