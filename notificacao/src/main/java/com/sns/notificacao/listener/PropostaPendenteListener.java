package com.sns.notificacao.listener;

import com.sns.notificacao.domain.Proposta;
import com.sns.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.sns.notificacao.constante.MensagemConstante.PROPOSTA_EM_ANALISE;

@Component
public class PropostaPendenteListener {

    private NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaPendente(Proposta proposta) {

        String mensagem = String.format(PROPOSTA_EM_ANALISE, proposta.getUsuario().getNome());

        notificacaoSnsService.notificar(mensagem);

    }
}
