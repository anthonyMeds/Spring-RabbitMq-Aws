package com.proposta.proposta_app.repository;

import com.proposta.proposta_app.entity.Proposta;
import org.springframework.data.repository.CrudRepository;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {
}