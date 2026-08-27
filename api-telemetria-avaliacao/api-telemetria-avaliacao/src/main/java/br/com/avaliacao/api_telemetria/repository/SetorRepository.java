package br.com.avaliacao.api_telemetria.repository;

import br.com.avaliacao.api_telemetria.entity.Motor;
import br.com.avaliacao.api_telemetria.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de Setor que irá se conectar com a tabela 'setores' no banco de dados
 */
@Repository
public interface SetorRepository extends JpaRepository<Setor, Integer> {

    /**
     * Função de verificação se o nome informado existe, independente
     * de maiúsculas e minúsculas (busca case-insensitive)
     * @param nome nome do setor informado como parâmetro de pesquisa
     * @return true: caso nome exista; false: caso nome não exista
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Função de verificação se existe alguma entidade com o nome informado,
     * mas que o Id seja diferente do informado
     * @param nome nome do setor informado como parâmetro de pesquisa
     * @param id identificador do setor a ser ignorado
     * @return true: caso exista uma entidade com o nome informado, mas diferente do ID;
     * false: caso não exista uma entidade com o nome informado, desconsiderando ID
     */
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Integer id);
}
