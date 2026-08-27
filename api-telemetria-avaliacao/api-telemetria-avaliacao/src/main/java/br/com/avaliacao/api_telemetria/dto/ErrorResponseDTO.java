package br.com.avaliacao.api_telemetria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Tratamento de como deve ser o corpo das mensagens de erro genéricas
 * @param timestamp Data e hora que o erro ocorreu
 * @param status status de erro recebido
 * @param error descrição breve do erro
 * @param message descrição detalhada do erro
 * @param fieldErrors campo onde o erro foi gerado
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

@Schema(description = "Tratamento de como deve ser o corpo das mensagens de erro")
public record ErrorResponseDTO(
        @Schema(
                description = "Data e horário dado em que o erro ocorreu",
                example = "26/08/2026T16:01"
        )
        LocalDateTime timestamp,

        @Schema(
                description = "Status HTTP de erro",
                example = "404"
        )
        Integer status,

        @Schema(
                description = "Mensagem de erro simplificada",
                example = "Motor não encontrado"
        )
        String error,

        @Schema(
                description = "Mensagem detalhada de erro",
                example = "Motor não encontrado pelo ID informado"
        )
        String message,

        @Schema(
                description = "Campo onde o erro foi gerado",
                example = "MethodArgumentNotValidException"
        )
        Map<String, String> fieldErrors
) {
    public ErrorResponseDTO(Integer status, String error, String message, Map<String, String> fieldErrors) {
        this(LocalDateTime.now(), status, error, message, fieldErrors);
    }

    public ErrorResponseDTO(Integer status, String error, String message) {
        this(null, status, error, message, Map.of());
    }
}
