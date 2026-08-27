package br.com.avaliacao.api_telemetria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Atributos recebidos pela requisição de gerenciamento de Setor
 * @param nome nome do setor
 * @param localizacao localização do setor
 */

@Schema(description = "DTO de requisição de gerenciamento de Setor")
public record SetorRequestDTO (

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
){

}
