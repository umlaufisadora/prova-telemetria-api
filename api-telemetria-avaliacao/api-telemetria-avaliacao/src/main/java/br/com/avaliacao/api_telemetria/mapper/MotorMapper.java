package br.com.avaliacao.api_telemetria.mapper;

import br.com.avaliacao.api_telemetria.dto.MotorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.MotorResponseDTO;
import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.entity.Setor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MotorMapper {

    /**
     * Mapper para transformar requisição de motor em entidade
     * @param dto requisição com dados representativos de Motor
     * @param setor Entidade setor, que é necessária para conversão da entidade Motor
     * @return Entidade construida de Motor
     */
    public Motor toEntity(MotorRequestDTO dto, Setor setor) {
        if (dto == null) {
            return null;
        }

        return Motor.builder()
                .setor(setor)
                .codigoAtivo(dto.codigoAtivo())
                .fabricante(dto.fabricante())
                .modelo(dto.modelo())
                .potenciaKw(dto.potenciaKw())
                .rpmNominal(dto.rpmNominal())
                .dataInstalacao(dto.dataInstalacao())
                .statusAtual(dto.statusAtual() != null ? dto.statusAtual() : "Operando")
                .build();
    }

    /**
     * Mapper para transformar entidade de Motor em response (DTO)
     * @param entity Entidade com dados de Motor a ser convertida
     * @return Response de Motor (DTO)
     */
    public MotorResponseDTO toDTO(Motor entity) {
        if (entity == null) {
            return null;
        }

        Integer setorId = entity.getSetor() != null ? entity.getSetor().getId() : null;
        String setorNome = entity.getSetor() != null ? entity.getSetor().getNome() : null;

        return new MotorResponseDTO(
                entity.getId(),
                setorId,
                setorNome,
                entity.getCodigoAtivo(),
                entity.getFabricante(),
                entity.getModelo(),
                entity.getPotenciaKw(),
                entity.getRpmNominal(),
                entity.getDataInstalacao(),
                entity.getStatusAtual()
        );
    }
}
