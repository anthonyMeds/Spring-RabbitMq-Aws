package com.sns.notificacao.listener;

import com.sns.notificacao.constante.MensagemConstante;
import com.sns.notificacao.domain.Proposta;
import com.sns.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaPendenteListener {

    @Autowired
    private NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaPendente(Proposta proposta) {

        String mensagem = String.format(MensagemConstante.PROPOSTA_EM_ANALISE, proposta.getUsuario().getNome());
        String telefone = proposta.getUsuario().getTelefone();

        notificacaoSnsService.notificar(telefone, mensagem);

    }
}
