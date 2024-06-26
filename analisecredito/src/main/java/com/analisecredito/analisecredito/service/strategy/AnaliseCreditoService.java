package com.analisecredito.analisecredito.service.strategy;

import com.analisecredito.analisecredito.domain.Proposta;
import com.analisecredito.analisecredito.exception.StrategyException;
import com.analisecredito.analisecredito.service.strategy.impl.NotificacaoRabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {

    @Autowired
    private List<CalculoPonto> calculoPontoList;

    @Autowired
    private NotificacaoRabbitMQService notificacaoRabbitMQService;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    public void analisar(Proposta proposta) {

        try {

            int soma =
                    calculoPontoList
                            .stream()
                            .mapToInt(impl -> impl.calcularPonto(proposta)).sum() ;

            proposta.setAprovada(soma > 350);

        } catch (StrategyException e) {
            proposta.setAprovada(false);
            proposta.setObservacao(e.getMessage());
        }

        notificacaoRabbitMQService.notificar(exchangePropostaConcluida, proposta);

    }

}
