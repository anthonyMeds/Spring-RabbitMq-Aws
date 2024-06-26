package com.analisecredito.analisecredito.service.strategy.impl;

import com.analisecredito.analisecredito.domain.Proposta;
import com.analisecredito.analisecredito.exception.StrategyException;
import com.analisecredito.analisecredito.service.strategy.CalculoPonto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.analisecredito.analisecredito.constante.MensagemConstante.PONTUACAO_BAIXA;

@Order(2)
@Component
public class PontuacaoScoreImpl implements CalculoPonto {

    @Override
    public int calcularPonto(Proposta proposta) {

        int score = score();

        if (score < 200) {
            throw new StrategyException(String.format(PONTUACAO_BAIXA, proposta.getUsuario().getNome()));
        } else if (score <= 400) {
            return 150;
        } else if (score <= 600) {
            return 180;
        } else {
            return 220;
        }

    }


    private int score() {
        return new Random().nextInt(0,1000);
    }

}
