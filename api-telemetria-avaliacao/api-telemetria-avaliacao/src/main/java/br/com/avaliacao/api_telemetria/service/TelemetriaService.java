package br.com.avaliacao.api_telemetria.service;

import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaResponseDTO;
import br.com.avaliacao.api_telemetria.entity.HistoricoTelemetria;
import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.mapper.TelemetriaMapper;
import br.com.avaliacao.api_telemetria.repository.HistoricoTelemetriaRepository;
import br.com.avaliacao.api_telemetria.repository.MotorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço que centraliza as regras de negócio relacionadas HistoricoTelemetria
 */
@Service
public class TelemetriaService {

    HistoricoTelemetriaRepository telemetriaRepository;
    MotorRepository motorRepository;
    TelemetriaMapper telemetriaMapper;

    public TelemetriaService(HistoricoTelemetriaRepository telemetriaRepository, MotorRepository motorRepository, TelemetriaMapper telemetriaMapper) {
        this.telemetriaRepository = telemetriaRepository;
        this.motorRepository = motorRepository;
        this.telemetriaMapper = telemetriaMapper;
    }

    /**
     * Registrar telemetria
     * @param dto request com os dados necessários para registrar Telemetria
     * @return DTO {@link TelemetriaResponseDTO} com dados representativos de HistoricoTelemetria
     */
    @Transactional
    public TelemetriaResponseDTO registrar(TelemetriaRequestDTO dto) {
        Motor motor = motorRepository.findById(dto.motorId().longValue())
                .orElseThrow(() -> new EntityNotFoundException("Motor não encontrado com ID: " + dto.motorId()));

        HistoricoTelemetria telemetria = telemetriaMapper.toEntity(dto, motor);
        HistoricoTelemetria salva = telemetriaRepository.save(telemetria);

        return telemetriaMapper.toDTO(salva);
    }

    /**
     * Listar o histórico de telemetria registrado (apenas leitura)
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Página de DTOs {@link TelemetriaResponseDTO} com dados representativos de HistoricoTelemetria
     */
    @Transactional(readOnly = true)
    public Page<TelemetriaResponseDTO> listarTodos(Pageable pageable) {
        return telemetriaRepository.findAll(pageable)
                .map(telemetriaMapper::toDTO);
    }

    /**
     * Buscar por Histórico de Telemetria por ID (apenas leitura)
     * @param id Identificador único de HistoricoTelemetria
     * @return DTO {@link TelemetriaResponseDTO} com dados representativos de HistoricoTelemetria
     */
    @Transactional(readOnly = true)
    public TelemetriaResponseDTO buscarPorId(Long id) {
        return telemetriaRepository.findById(id)
                .map(telemetriaMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Registro de telemetria não encontrado para o ID: " + id));
    }

    /**
     * Buscar histórico de telemetria a partir de Motor
     * @param motorId Identificador único de Motor
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Página de DTOs {@link TelemetriaResponseDTO} com dados representativos
     * de HistoricoTelemetria a partir do Motor informado
     */
    @Transactional(readOnly = true)
    public Page<TelemetriaResponseDTO> buscarPorMotor(Integer motorId, Pageable pageable) {
        if (!motorRepository.existsById(motorId.longValue())) {
            throw new EntityNotFoundException("Motor não encontrado com ID: " + motorId);
        }
        return telemetriaRepository.findByMotorIdOrderByDataHoraDesc(motorId, pageable)
                .map(telemetriaMapper::toDTO);
    }
}
