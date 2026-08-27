package br.com.avaliacao.api_telemetria.service;

import br.com.avaliacao.api_telemetria.dto.SetorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.entity.Setor;
import br.com.avaliacao.api_telemetria.mapper.SetorMapper;
import br.com.avaliacao.api_telemetria.repository.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço que centraliza as regras de negócio relacionadas Setor
 */
@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final SetorMapper setorMapper;

    public SetorService(SetorRepository setorRepository, SetorMapper setorMapper)
    {
        this.setorRepository = setorRepository;
        this.setorMapper = setorMapper;
    }

    /**
     * Criação de Setor
     * @param dto request com os dados necessários para criação de Entidade Setor
     * @return DTO {@link SetorResponseDTO} com dados representativos de Setor
     */
    @Transactional
    public SetorResponseDTO criar(SetorRequestDTO dto) {
        if (setorRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new IllegalArgumentException("Já existe um setor cadastrado com o nome: " + dto.nome());
        }
        Setor setor = setorMapper.toEntity(dto);
        Setor save = setorRepository.save(setor);
        return setorMapper.toDTO(save);
    }

    /**
     * Listar todos os setores cadastrados (apenas leitura)
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Página de DTOs {@link SetorResponseDTO} com dados representativos de Motor
     */
    @Transactional(readOnly = true)
    public Page<SetorResponseDTO> listarTodos(Pageable pageable) {
        return setorRepository.findAll(pageable)
                .map(setorMapper::toDTO);
    }

    /**
     * Buscar por ID (apenas leitura)
     * @param id Identificador único de Setor
     * @return DTO {@link SetorResponseDTO} com dados representativos de Setor
     */
    @Transactional(readOnly = true)
    public SetorResponseDTO buscarPorId(Integer id) {
        return setorRepository.findById(id)
                .map(setorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado para o ID: " + id));
    }

    /**
     * Atualizar setor pelo ID
     * @param id Identificador único de setor
     * @param dto request com os dados necessários para alterar os dados da Entidade Setor
     * @return DTO {@link SetorResponseDTO} com dados representativos de Setor
     */
    @Transactional
    public SetorResponseDTO atualizar(Integer id, SetorRequestDTO dto) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado para o ID: " + id));

        if (setorRepository.existsByNomeIgnoreCaseAndIdNot(dto.nome(), id)) {
            throw new IllegalArgumentException("Já existe outro setor cadastrado com o nome: " + dto.nome());
        }

        setor.setNome(dto.nome());
        setor.setLocalizacao(dto.localizacao());

        return setorMapper.toDTO(setorRepository.save(setor));
    }
}
