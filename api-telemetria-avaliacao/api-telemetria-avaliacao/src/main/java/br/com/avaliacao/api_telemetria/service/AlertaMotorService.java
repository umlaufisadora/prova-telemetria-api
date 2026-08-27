package br.com.avaliacao.api_telemetria.service;

import br.com.avaliacao.api_telemetria.dto.AlertaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResolucaoDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResponseDTO;
import br.com.avaliacao.api_telemetria.entity.AlertaMotor;
import br.com.avaliacao.api_telemetria.entity.HistoricoTelemetria;
import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.mapper.AlertaMapper;
import br.com.avaliacao.api_telemetria.repository.AlertaMotorRepository;
import br.com.avaliacao.api_telemetria.repository.MotorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço que centraliza as regras de negócio relacionadas AlertaMotor
 */
@Service
public class AlertaMotorService
{
    private final AlertaMapper alertaMapper;
    private final AlertaMotorRepository alertaMotorRepository;
    private final MotorRepository motorRepository;

    public AlertaMotorService(AlertaMapper alertaMapper,
                              AlertaMotorRepository alertaMotorRepository,
                              MotorRepository motorRepository) {
        this.alertaMapper = alertaMapper;
        this.alertaMotorRepository = alertaMotorRepository;
        this.motorRepository = motorRepository;
    }

    /**
     * Criação de Alertas de Motores
     * @param dto request com os dados necessários para criação da Entidade AlertaMotor
     * @return DTO {@link AlertaResponseDTO} com dados representativos de AlertaMotor
     */
    @Transactional
    public AlertaResponseDTO criar(AlertaRequestDTO dto)
    {
        Motor motor = motorRepository.findById(dto.motorId().longValue())
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado com ID: " + dto.motorId()));

        AlertaMotor alertaMotor = alertaMapper.toEntity(dto,motor);
        AlertaMotor salva = alertaMotorRepository.save(alertaMotor);

        return alertaMapper.toDTO(salva);
    }

    /**
     * Listar todos os alertas de motores cadatrados (apenas leitura)
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Página de DTOs {@link AlertaResponseDTO} com dados representativos de AlertaMotor
     */
    @Transactional(readOnly = true)
    public Page<AlertaResponseDTO> listarTodos(Pageable pageable)
    {
        return alertaMotorRepository.findAll(pageable)
                .map(alertaMapper::toDTO);
    }

    /**
     * Buscar AlertaMotor por ID (apenas leitura)
     * @param id Identificador único de AlertaMotor
     * @return DTO {@link AlertaResponseDTO} com dados representativos de AlertaMotor
     */
    @Transactional(readOnly = true)
    public AlertaResponseDTO buscarPorId(Integer id)
    {
        return alertaMotorRepository.findById(id)
                .map(alertaMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Alerta não encontrado pelo ID: " + id));
    }

    /**
     * Listar todos os alertas por status de resolução
     * @param resolvido parâmetro informado como status
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Página de DTOs {@link AlertaResponseDTO} com dados representativos de AlertaMotor
     */
    @Transactional(readOnly = true)
    public Page<AlertaResponseDTO> listarPorStatusResolucao(Boolean resolvido, Pageable pageable)
    {
        return alertaMotorRepository.findAllOrderByResolvido(resolvido, pageable)
                .map(alertaMapper::toDTO);
    }

    /**
     * Buscar Alertas por Motor
     * @param motorId Identificador único de motor
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Página de DTOs {@link AlertaResponseDTO} com dados representativos de AlertaMotor
     */
    @Transactional
    public @Nullable Page<AlertaResponseDTO> buscarPorMotor(Integer motorId, Pageable pageable)
    {
        return alertaMotorRepository.findByMotorId(motorId, pageable)
                .map(alertaMapper::toDTO);
    }

    /**
     * Atualizar apenas o status de resolução
     * @param id Identificador único de AlertaMotor
     * @param dto request com dado necessário para alterar o status de resolução
     * @return DTO {@link AlertaResponseDTO}
     */
    @Transactional
    public @Nullable AlertaResponseDTO atualizarStatusResolucao(Integer id, @Valid AlertaResolucaoDTO dto)
    {
        AlertaMotor alertaMotor = alertaMotorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alerta de Motor não encontrado pelo ID: " + id));

        alertaMotor.setResolvido(dto.resolvido());
        return alertaMapper.toDTO(alertaMotorRepository.save(alertaMotor));
    }
}
