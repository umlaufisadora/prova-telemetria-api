package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Atributos devolvidos pela resposta de gerenciamento de AlertaMotor
 * @param id
 * @param motorId
 * @param codigoAtivoMotor
 * @param dataAlerta
 * @param tipoAnomalia
 * @param criticidade
 * @param descricao
 * @param resolvido
 */

@Schema(description = "DTO da resposta de gerenciamento de AlertaMotor")
public record AlertaResponseDTO(

        @Schema(
                description = "Identificador único de AlertaMotor",
                example = "1"
        )
        @NotNull(message = "O ID do AlertaMotor é obrigatório")
        Integer id,

        @Schema(
                description = "Identificador único de Motor",
                example = "1"
        )
        @NotNull(message = "O ID do motor é obrigatório")
        Integer motorId,

        @Schema(
                description = "Código do ativo do motor",
                example = "ABC-1234"
        )
        @Size(max=50, message = "O código do ativo do motor pode ter até 50 caracteres")
        @NotBlank(message = "O código do ativo do motor é obrigatório")
        String codigoAtivoMotor,

        @Schema(
                description = "Data e horário em que o alerta foi criado",
                example = "2026-08-26T16:01"
        )
        LocalDateTime dataAlerta,

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
        String descricao,

        @Schema(
                description = "Status de resolução do alerta",
                example = "True"
        )
        @NotNull(message = "O status de resolução é obrigatório")
        Boolean resolvido
) {
}
