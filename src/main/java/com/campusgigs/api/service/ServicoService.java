package com.campusgigs.api.service;

import com.campusgigs.api.dto.CriarServicoRequest;
import com.campusgigs.api.dto.ServicoResponse;
import com.campusgigs.api.model.Servico;
import com.campusgigs.api.model.SituacaoServico;
import com.campusgigs.api.model.Usuario;
import com.campusgigs.api.repository.ServicoRepository;
import com.campusgigs.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final UsuarioRepository usuarioRepository;

    public ServicoResponse publicar(CriarServicoRequest request, String emailPrestador) {
        Usuario prestador = buscarUsuario(emailPrestador);

        Servico servico = Servico.builder()
                .prestador(prestador)
                .titulo(request.titulo())
                .descricao(request.descricao())
                .categoria(request.categoria())
                .preco(request.preco())
                .build();

        Servico salvo = servicoRepository.save(servico);
        return ServicoResponse.from(salvo);
    }

    public List<ServicoResponse> listar() {
        return servicoRepository.findAll().stream()
                .map(ServicoResponse::from)
                .toList();
    }

    public ServicoResponse encerrar(Long servicoId, String emailUsuarioLogado, boolean isAdmin) {
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        boolean ehDono = servico.getPrestador().getEmail().equals(emailUsuarioLogado);

        if (!ehDono && !isAdmin) {
            throw new AccessDeniedException("Você não tem permissão para encerrar este serviço");
        }

        servico.setSituacao(SituacaoServico.ENCERRADO);
        Servico salvo = servicoRepository.save(servico);
        return ServicoResponse.from(salvo);
    }

    private Usuario buscarUsuario(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
}