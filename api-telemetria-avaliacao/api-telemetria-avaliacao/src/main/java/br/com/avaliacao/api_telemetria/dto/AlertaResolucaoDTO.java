package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.awt.*;

/**
 * Alterar status do atributo resolvido em alertaMotor
 * @param resolvido status do alerta do motor
 */

@Schema(description = "DTO que altera o status de resolvido dentro de uma entidade AlertaMotor")
public record AlertaResolucaoDTO(
        @Schema(
                description = "Status de resolução do alerta",
                example = "True"
        )
        @NotNull(message = "O status de resolução é obrigatório")
        Boolean resolvido
) {
}
