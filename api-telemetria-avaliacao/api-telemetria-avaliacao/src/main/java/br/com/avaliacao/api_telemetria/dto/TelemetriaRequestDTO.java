package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Atributos recebidos pela requisição de gerenciamento de HistoricoTelemetria
 * @param motorId Identificador único de motor
 * @param temperaturaCarcaca temperatura da carcaça do motor
 * @param rpmAtual rotações por minuto atual
 * @param vibracaoGlobal vibração global do motor
 */

@Schema(description = "DTO de requisição de gerenciamento de HistoricoTelemetria")
public record TelemetriaRequestDTO(

        @Schema(
                description = "Identificador único do motor",
                example = "1"
        )
        @NotNull(message = "O ID do motor é obrigatório")
        Integer motorId,

        @Schema(
                description = "Temperatura da carcaça",
                example = "78.3"
        )
        @NotNull(message = "A temperatura é obrigatória")
        BigDecimal temperaturaCarcaca,

        @Schema(
                description = "O RPM atual do motor",
                example = "1500"
        )
        @NotNull(message = "O RPM é obrigatório")
        Integer rpmAtual,

        @Schema(
                description = "A vibração global do motor",
                example = "155.9"
        )
        @NotNull(message = "A vibração é obrigatória")
        BigDecimal vibracaoGlobal
) {
}
