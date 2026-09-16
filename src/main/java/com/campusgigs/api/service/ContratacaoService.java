package com.campusgigs.api.service;

import com.campusgigs.api.dto.ContratacaoResponse;
import com.campusgigs.api.model.Contratacao;
import com.campusgigs.api.model.Servico;
import com.campusgigs.api.model.SituacaoServico;
import com.campusgigs.api.model.Usuario;
import com.campusgigs.api.repository.ContratacaoRepository;
import com.campusgigs.api.repository.ServicoRepository;
import com.campusgigs.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContratacaoService {

    private final ContratacaoRepository contratacaoRepository;
    private final ServicoRepository servicoRepository;
    private final UsuarioRepository usuarioRepository;

    public ContratacaoResponse contratar(Long servicoId, String emailContratante) {
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        if (servico.getSituacao() != SituacaoServico.ATIVO) {
            throw new IllegalStateException("Só é possível contratar um serviço ativo");
        }

        Usuario contratante = usuarioRepository.findByEmail(emailContratante)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (servico.getPrestador().getId().equals(contratante.getId())) {
            throw new IllegalArgumentException("Você não pode contratar o próprio serviço");
        }

        Contratacao contratacao = Contratacao.builder()
                .servico(servico)
                .contratante(contratante)
                .build();

        Contratacao salva = contratacaoRepository.save(contratacao);
        return ContratacaoResponse.from(salva);
    }
}