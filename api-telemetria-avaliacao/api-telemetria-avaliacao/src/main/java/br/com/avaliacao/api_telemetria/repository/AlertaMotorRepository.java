package br.com.avaliacao.api_telemetria.repository;

import br.com.avaliacao.api_telemetria.entity.AlertaMotor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de AlertaMotor que irá se conectar com a tabela 'alertas_motores' no banco de dados
 */
@Repository
public interface AlertaMotorRepository extends JpaRepository<AlertaMotor, Integer>
{
    /**
     * Função de retornar todos os alertas cadastrando ordenando-os por seu status
     * @param resolvido parâmetro de Status a ser ordenado
     * @param pageable página com parâmetros de retorno da busca (ex: size = 20)
     * @return Página resultante retornando apenas uma quantidade determinada
     * (ex: size = 20) da entidade AlertaMotor
     */
    Page<AlertaMotor> findAllOrderByResolvido(Boolean resolvido, Pageable pageable);

    /**
     * Função de pesquisa no banco de dados para encontrar motores por
     * id e ordernar eles pelo atributo 'data_hora' em ordem decrescente
     * @param motorId Identificador único de motor
     * @param pageable Página resultante da pesquisa
     * @return Página resultante retornando apenas uma quantidade determinada
     * (ex: size = 20) da entidade AlertaMotor
     */
    Page<AlertaMotor> findByMotorId(Integer motorId, Pageable pageable);
}
