package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Atributos devolvidos pela resposta de gerenciamento
 * @param id Identificador único de setor
 * @param nome nome do setor
 * @param localizacao localização do setor
 */
@Schema(description = "DTO da resposta de gerenciamento de Setor")
public record SetorResponseDTO(

        @Schema(
                description = "Identificador único do setor",
                example = "1"
        )
        @NotNull(message = "O ID do Setor é obrigatório")
        Integer id,
        @Schema(
                description = "Nome do setor",
                example = "TI"
        )
        @NotBlank(message = "O nome do setor é obrigatório")
        @Size(max = 100, message = "O nome do setor deve ter no máximo 100 caracteres")
        String nome,

        @Schema(
                description = "Localização do setor",
                example = "WEG II Extensão"
        )
        @Size(max = 100, message = "A localização deve ter no máximo 100 caracteres")
        String localizacao
) {
}
