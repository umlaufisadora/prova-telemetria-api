package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Atributos recebidos pela requisição de gerenciamento de AlertaMotor
 * @param motorId Identificador único de motor
 * @param tipoAnomalia descrição do tipo de anomalia
 * @param criticidade estado crítico do alerta
 * @param descricao descrição detalhada do alerta
 */

@Schema(description = "DTO de requisição de gerenciamento de AlertaMotor")
public record AlertaRequestDTO(
        @Schema(
                description = "Identificador único do motor",
                example = "1"
        )
        @NotNull(message = "O ID do motor é obrigatório")
        Integer motorId,

        @Schema(
                description = "Descrição do tipo de anomalia encontrada",
                example = "Motor está fazendo som estranho"
        )
        @NotBlank(message = "O tipo de anomalia é obrigatório")
        @Size(max = 100, message = "O tipo de anomalia deve ter no máximo 100 caracteres")
        String tipoAnomalia,

        @Schema(
                description = "Descrição do nível crítico (criticidade) da anomalia",
                example = "Muito alto"
        )
        @NotBlank(message = "A criticidade é obrigatória")
        @Size(max = 20, message = "A criticidade deve ter no máximo 20 caracteres")
        String criticidade,

        @Schema(
                description = "Descrição detalhada da situação em que o motor se encontra",
                example = "Motor está fazendo som ao ser ligado, como se um corpo estranho estivesse dentro dele"
        )
        String descricao
){

}
