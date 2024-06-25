package com.analisecredito.analisecredito.service.strategy.impl;

import com.analisecredito.analisecredito.domain.Proposta;
import com.analisecredito.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class NomeNegativadoImpl implements CalculoPonto {

    @Override
    public int calcularPonto(Proposta proposta) {

        if (nomeNegativado()){
            throw new RuntimeException("Nome negativado.");
        }

        return 100;
    }

    private boolean nomeNegativado() {
        return new Random().nextBoolean();
    }

}
