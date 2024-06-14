package com.proposta.proposta_app.agendador;

import com.proposta.proposta_app.repository.PropostaRepository;
import com.proposta.proposta_app.service.NotificacaoRabbitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropostaSemIntegracao {

    private  PropostaRepository propostaRepository;
    private NotificacaoRabbitService notificacaoRabbitService;
    private String exchange;

    public PropostaSemIntegracao(PropostaRepository propostaRepository,
                                 NotificacaoRabbitService notificacaoRabbitService,
                                 @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    public void buscarPropostaSemIntegracao() {

        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoRabbitService.notificar(proposta,exchange);

                proposta.setIntegrada(true);
                propostaRepository.save(proposta);

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }

        });
    }


}
