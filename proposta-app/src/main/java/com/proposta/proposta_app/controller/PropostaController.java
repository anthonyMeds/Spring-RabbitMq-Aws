package com.proposta.proposta_app.controller;

import com.proposta.proposta_app.dto.PropostaRequestDto;
import com.proposta.proposta_app.dto.PropostaResponseDto;
import com.proposta.proposta_app.service.PropostaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDto> criar
            (
                    @RequestBody PropostaRequestDto requestDto
            ) {

        PropostaResponseDto criar = propostaService.criar(requestDto);

        return ResponseEntity.ok(criar);
    }

}
