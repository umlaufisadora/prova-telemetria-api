package br.com.avaliacao.api_telemetria.repository;

import br.com.avaliacao.api_telemetria.entity.Motor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório de Motor que irá se conectar com a tabela 'motores' no banco de dados
 */
@Repository
public interface MotorRepository extends JpaRepository<Motor, Long>{

    /**
     * Função de pesquisa no bando de dados pelo atributo codigoAtivo
     * @param codigoAtivo parâmetro informado usado para buscar entidade no banco de dados
     * @return Pode ou não retornar uma entidade Motor que tem o código
     * do ativo igual ao informado
     */
    Optional<Motor> findByCodigoAtivo(String codigoAtivo);

    /**
     * Função de verificação se existe alguma entidade com o código ativo informado
     * @param codigoAtivo parâmetro informado usado para verificar existência de código
     * do ativo no banco de dados
     * @return true: caso exista uma entidade com o valor informado; false: caso não exista
     * uma entidade com valor informado
     */
    boolean existsByCodigoAtivo(String codigoAtivo);

    /**
     * Função de verificação se existe alguma entidade com o código ativo
     * informado, mas não com o ID informado
     * @param codigoAtivo código do ativo informado como parâmetro de pesquisa
     * @param id identificador do motor a ser ignorado
     * @return true: caso exista uma entidade com o código informado, mas diferente do ID;
     * false: caso não exista uma entidade com código do ativo, desconsiderando ID
     */
    boolean existsByCodigoAtivoAndIdNot(String codigoAtivo, Integer id);
}
