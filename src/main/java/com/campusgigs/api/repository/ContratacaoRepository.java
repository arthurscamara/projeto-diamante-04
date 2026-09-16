package com.campusgigs.api.repository;

import com.campusgigs.api.model.Contratacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratacaoRepository extends JpaRepository<Contratacao, Long> {
}