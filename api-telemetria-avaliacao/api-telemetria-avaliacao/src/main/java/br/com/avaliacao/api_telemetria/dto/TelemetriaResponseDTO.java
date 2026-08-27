package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Atributos recebidos pela requisição de gerenciamento de HistoricoTelemetria
 * @param id Identificação única de telemetria
 * @param motorId Identificação única de motor
 * @param dataHota data e hora que foi marcada a telemetria
 * @param temperaturaCarcaca temperatura da carcaça do motor
 * @param rpmAtual rotações por minuto atual
 * @param vibracaoGlobal vibração global do motor
 */

@Schema(description = "DTO de requisição de gerenciamento de HistoricoTelemetria")
public record TelemetriaResponseDTO(
        @Schema(
                description = "Identificador único do histórico de telemetria",
                example = "1"
        )
        @NotNull(message = "O ID do histórico de telemetria é obrigatório")
        Long id,

        @Schema(
                description = "Identificador único do motor",
                example = "1"
        )
        @NotNull(message = "O ID do motor é obrigatório")
        Integer motorId,

        @Schema(
                description = "Data e hora do acontecimento",
                example = "2026-08-26T16:01"
        )
        LocalDateTime dataHota,

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
){
}
