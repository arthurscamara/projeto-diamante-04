package com.campusgigs.api.controller;

import com.campusgigs.api.dto.ContratacaoResponse;
import com.campusgigs.api.service.ContratacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ContratacaoController {

    private final ContratacaoService contratacaoService;

    @PostMapping("/{id}/contratar")
    public ResponseEntity<ContratacaoResponse> contratar(@PathVariable Long id, Authentication authentication) {
        ContratacaoResponse response = contratacaoService.contratar(id, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}