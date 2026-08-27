package br.com.avaliacao.api_telemetria.mapper;

import br.com.avaliacao.api_telemetria.dto.SetorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaResponseDTO;
import br.com.avaliacao.api_telemetria.entity.HistoricoTelemetria;
import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.entity.Setor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SetorMapper {

    /**
     * Mapper para transformar requisição de Setor em entidade
     * @param dto requisição com dados representativos de Setor
     * @return Entidade construida de Setor
     */
    public Setor toEntity(SetorRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Setor.builder()
                .nome(dto.nome())
                .localizacao(dto.localizacao())
                .build();
    }

    /**
     * Mapper para transformar entidade de Setor em response (DTO)
     * @param entity Entidade com dados de Setor a ser convertida
     * @return Response de Setor (DTO)
     */
    public SetorResponseDTO toDTO(Setor entity) {
        if (entity == null) {
            return null;
        }

        return new SetorResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getLocalizacao()
        );
    }
}
