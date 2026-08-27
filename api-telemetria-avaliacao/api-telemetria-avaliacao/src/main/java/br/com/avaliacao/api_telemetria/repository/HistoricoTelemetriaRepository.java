package br.com.avaliacao.api_telemetria.repository;

import br.com.avaliacao.api_telemetria.entity.HistoricoTelemetria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositório de HistoricoTelemetria que irá se conectar com a tabela 'historico_telemetria' no banco de dados
 */
@Repository
public interface HistoricoTelemetriaRepository extends JpaRepository<HistoricoTelemetria, Long> {

    /**
     * Função de pesquisa no banco de dados para encontrar motores por
     * id e ordernar eles pelo atributo 'data_hora' em ordem decrescente
     * @param motorId Identificador único de motor
     * @param pageable Página resultante da pesquisa
     * @return Página resultante retornando apenas uma quantidade determinada
     * (ex: size = 20) da entidade HistoricoTelemetria
     */
    Page<HistoricoTelemetria> findByMotorIdOrderByDataHoraDesc(Integer motorId, Pageable pageable);
}