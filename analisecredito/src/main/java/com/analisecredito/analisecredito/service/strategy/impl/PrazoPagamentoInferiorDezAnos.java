package com.analisecredito.analisecredito.service.strategy.impl;

import com.analisecredito.analisecredito.domain.Proposta;
import com.analisecredito.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

@Component
public class PrazoPagamentoInferiorDezAnos implements CalculoPonto {
    @Override
    public int calcularPonto(Proposta proposta) {
        return proposta.getPrazoPagamento() < 120 ?  80: 0;
    }
}
