package com.analisecredito.analisecredito.service.strategy;

import com.analisecredito.analisecredito.domain.Proposta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {


    private List<CalculoPonto> calculoPontoList;

    public AnaliseCreditoService(List<CalculoPonto> calculoPontoList) {
        this.calculoPontoList = calculoPontoList;
    }

    public void analisar(Proposta proposta) {

        int sum = calculoPontoList.stream().mapToInt(impl -> impl.calcularPonto(proposta)).sum();
        
    }

}
