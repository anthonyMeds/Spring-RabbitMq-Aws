package com.proposta.proposta_app.service;

import com.proposta.proposta_app.entity.Proposta;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificacaoService {

    RabbitTemplate rabbitTemplate;

    public void notificar(Proposta proposta, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposta);
    }

}
