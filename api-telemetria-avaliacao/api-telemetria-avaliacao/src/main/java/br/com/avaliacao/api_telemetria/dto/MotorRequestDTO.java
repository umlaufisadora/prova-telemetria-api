package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Atributos recebidos pela requisição de gerenciamento de Motor
 * @param setorId Identificador único de setor
 * @param codigoAtivo código de ativo do motor
 * @param fabricante empresa ou indivíduo que fabricou o motor
 * @param modelo modelo do motor
 * @param potenciaKw potência do motor em Kw
 * @param rpmNominal rotações por minuto nominal do motor
 * @param dataInstalacao data de instalação
 * @param statusAtual status atual do motor
 */

@Schema(description = "DTO de requisição de gerenciamento de Motor")
public record MotorRequestDTO(
        @Schema(
                description = "Identificador único de setor",
                example = "1"
        )
        @NotNull(message = "O ID de setor é obrigatório")
        Integer setorId,

        @Schema(
                description = "O código de ativo do motor",
                example = "ABC-1234"
        )
        @NotBlank(message = "O código do ativo é obrigatório")
        @Size(max = 50, message = "O código do ativo deve ter no máximo 50 caracteres")
        String codigoAtivo,

        @Schema(
                description = "Fabricante do Motor",
                example = "WEG"
        )
        String fabricante,

        @Schema(
                description = "Modelo do motor",
                example = "Quattro 30"
        )
        String modelo,

        @Schema(
                description = "Potência do motor em Kw",
                example = "4Kw"
        )
        @Positive(message = "A potência deve ser um valor positivo")
        BigDecimal potenciaKw,

        @Schema(
                description = "O RPM nominal do motor",
                example = "1500"
        )
        @Positive(message = "O RPM nominal deve ser um valor positivo")
        Integer rpmNominal,

        @Schema(
                description = "A data de instalação do motor",
                example = "2026-08-26"
        )
        LocalDate dataInstalacao,

        @Schema(
                description = "O status atual em que o motor se encontra",
                example = "Normal"
        )
        @Size(max=30, message = "O status atual do motor deve ter até 30 caracteres")
        String statusAtual
) {
}
