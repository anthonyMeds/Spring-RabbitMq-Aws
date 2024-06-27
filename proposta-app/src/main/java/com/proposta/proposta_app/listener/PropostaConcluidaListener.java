package com.proposta.proposta_app.listener;

import com.proposta.proposta_app.dto.PropostaResponseDto;
import com.proposta.proposta_app.entity.Proposta;
import com.proposta.proposta_app.mapper.PropostaMapper;
import com.proposta.proposta_app.repository.PropostaRepository;
import com.proposta.proposta_app.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta) {
        propostaRepository.save(proposta);

        PropostaResponseDto propostaResponseDto = PropostaMapper.INSTANCE.convertEntityToDto(proposta);

        webSocketService.notificar(propostaResponseDto);
    }
}
