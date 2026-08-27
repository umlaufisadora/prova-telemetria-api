package br.com.avaliacao.api_telemetria.service;

import br.com.avaliacao.api_telemetria.dto.MotorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.MotorStatusUpdateDTO;
import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.entity.Setor;
import br.com.avaliacao.api_telemetria.mapper.MotorMapper;
import br.com.avaliacao.api_telemetria.repository.MotorRepository;
import br.com.avaliacao.api_telemetria.repository.SetorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço que centraliza as regras de negócio relacionadas Motor
 */
@Service
public class MotorService {
    private final MotorRepository motorRepository;
    private final SetorRepository setorRepository;
    private final MotorMapper motorMapper;

    public MotorService(MotorRepository motorRepository, SetorRepository setorRepository, MotorMapper motorMapper) {
        this.motorRepository = motorRepository;
        this.setorRepository = setorRepository;
        this.motorMapper = motorMapper;
    }

    /**
     * Criação de Motores
     * @param dto request com dados necesários para criação da Entidade Motor
     * @return DTO {@link MotorResponseDTO} com dados representativos de Motor
     */
    @Transactional
    public MotorResponseDTO criar(MotorRequestDTO dto) {
        if (motorRepository.existsByCodigoAtivo(dto.codigoAtivo())) {
            throw new IllegalArgumentException("Já existe um motor cadastrado com o código do ativo: " + dto.codigoAtivo());
        }

        Setor setor = buscarSetorSeInformado(dto.setorId());
        Motor motor = motorMapper.toEntity(dto, setor);

        return motorMapper.toDTO(motorRepository.save(motor));
    }

    /**
     * Listar todos os motores cadastrados (apenas leitura
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Página de DTOs {@link MotorResponseDTO} com dados representativos de Motor
     */
    @Transactional(readOnly = true)
    public Page<MotorResponseDTO> listarTodos(Pageable pageable) {
        return motorRepository.findAll(pageable)
                .map(motorMapper::toDTO);
    }

    /**
     * Buscar Motor por ID (apenas leitura)
     * @param id Identificador único de Motor
     * @return DTO {@link MotorResponseDTO} com dados representativos de Motor
     */
    @Transactional(readOnly = true)
    public MotorResponseDTO buscarPorId(Integer id) {
        return motorRepository.findById(id.longValue())
                .map(motorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado para o ID: " + id));
    }

    /**
     * Buscar por código ativo (apenas leitura)
     * @param codigoAtivo código do ativo do motor a ser buscado
     * @return DTO {@link MotorResponseDTO} com dados representativos de Motor
     */
    @Transactional(readOnly = true)
    public MotorResponseDTO buscarPorCodigoAtivo(String codigoAtivo) {
        return motorRepository.findByCodigoAtivo(codigoAtivo)
                .map(motorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado para o código: " + codigoAtivo));
    }

    /**
     * Atualizar Motor por ID
     * @param id Identificador único de motor
     * @param dto request com os dados necessários para alterar os dados da Entidade Motor
     * @return DTO {@link MotorResponseDTO} com dados representativos de Motor
     */
    @Transactional
    public MotorResponseDTO atualizar(Integer id, MotorRequestDTO dto) {
        Motor motor = motorRepository.findById(id.longValue())
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado para o ID: " + id));

        if (motorRepository.existsByCodigoAtivoAndIdNot(dto.codigoAtivo(), id)) {
            throw new IllegalArgumentException("O código de ativo informado já está em uso por outro motor.");
        }

        Setor setor = buscarSetorSeInformado(dto.setorId());

        motor.setSetor(setor);
        motor.setCodigoAtivo(dto.codigoAtivo());
        motor.setFabricante(dto.fabricante());
        motor.setModelo(dto.modelo());
        motor.setPotenciaKw(dto.potenciaKw());
        motor.setRpmNominal(dto.rpmNominal());
        motor.setDataInstalacao(dto.dataInstalacao());
        if (dto.statusAtual() != null) {
            motor.setStatusAtual(dto.statusAtual());
        }

        return motorMapper.toDTO(motorRepository.save(motor));
    }

    /**
     * Atualizar apenas o status pelo ID
     * @param id Identificador único de Motor
     * @param dto request com o dado necessário para alterar o status do Motor
     * @return DTO {@link MotorResponseDTO} com dados representativos de Motor
     */
    @Transactional
    public MotorResponseDTO atualizarStatus(Integer id, MotorStatusUpdateDTO dto) {
        Motor motor = motorRepository.findById(id.longValue())
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado para o ID: " + id));

        motor.setStatusAtual(dto.statusAtual());
        return motorMapper.toDTO(motorRepository.save(motor));
    }

    /**
     * Buscar setor (caso seja informado) por ID
     * @param setorId Identificador único de setor
     * @return entidade Setor cujo ID condiz com o valor informado
     */
    @Transactional
    private Setor buscarSetorSeInformado(Integer setorId) {
        if (setorId == null) {
            return null;
        }
        return setorRepository.findById(setorId)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado para o ID: " + setorId));
    }
}
