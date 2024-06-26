package com.analisecredito.analisecredito.service.strategy.impl;

import com.analisecredito.analisecredito.domain.Proposta;
import com.analisecredito.analisecredito.exception.StrategyException;
import com.analisecredito.analisecredito.service.strategy.CalculoPonto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.analisecredito.analisecredito.constante.MensagemConstante.CLIENTE_NEGATIVADO;

@Order(1)
@Component
public class NomeNegativadoImpl implements CalculoPonto {

    @Override
    public int calcularPonto(Proposta proposta) {

        if (nomeNegativado()){
            throw new StrategyException(String.format(CLIENTE_NEGATIVADO, proposta.getUsuario().getNome()));
        }

        return 100;
    }

    private boolean nomeNegativado() {
        return new Random().nextBoolean();
    }

}
