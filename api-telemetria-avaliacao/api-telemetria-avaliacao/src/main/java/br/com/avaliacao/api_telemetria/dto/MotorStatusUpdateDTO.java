package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Alterara o status atual do motor
 * @param statusAtual status atual do motor
 */
@Schema(description = "DTO que altera o status de resolvido dentro de uma entidade motor")
public record MotorStatusUpdateDTO(

        @Schema(
                description = "O status atual em que o motor se encontra",
                example = "Normal"
        )
        @NotBlank(message = "O status atual é obrigatório")
        @Size(max=30, message = "O status atual do motor deve ter até 30 caracteres")
        String statusAtual
) {
}
