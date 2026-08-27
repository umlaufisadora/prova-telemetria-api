package br.com.avaliacao.api_telemetria.mapper;

import br.com.avaliacao.api_telemetria.dto.AlertaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResponseDTO;
import br.com.avaliacao.api_telemetria.entity.AlertaMotor;
import br.com.avaliacao.api_telemetria.entity.Motor;
import org.springframework.stereotype.Component;

@Component
public class AlertaMapper {

    /**
     * Mapper para transformar requisição de alerta em entidade
     * @param dto requisição com dados representativos de AlertaMotor
     * @param motor Entidade motor, que é necessária para conversão da entidade AlertaMotor
     * @return Entidade construida de AlertaMotor
     */
    public AlertaMotor toEntity(AlertaRequestDTO dto, Motor motor) {
        if (dto == null) {
            return null;
        }

        return AlertaMotor.builder()
                .motor(motor)
                .tipoAnomalia(dto.tipoAnomalia())
                .criticidade(dto.criticidade())
                .descricao(dto.descricao())
                .build();
    }

    /**
     * Mapper para transformar entidade de alerta em response (DTO)
     * @param entity Entidade com dados de AlertaMotor a ser convertida
     * @return Response de AlertaMotor (DTO)
     */
    public AlertaResponseDTO toDTO(AlertaMotor entity) {
        if (entity == null) {
            return null;
        }

        Integer motorId = entity.getMotor() != null ? entity.getMotor().getId() : null;
        String codigoAtivo = entity.getMotor() != null ? entity.getMotor().getCodigoAtivo() : null;

        return new AlertaResponseDTO(
                entity.getId(),
                motorId,
                codigoAtivo,
                entity.getDataAlerta(),
                entity.getTipoAnomalia(),
                entity.getCriticidade(),
                entity.getDescricao(),
                entity.getResolvido()
        );
    }
}
