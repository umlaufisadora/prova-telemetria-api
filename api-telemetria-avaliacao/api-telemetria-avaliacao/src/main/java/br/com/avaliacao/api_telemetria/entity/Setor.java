package br.com.avaliacao.api_telemetria.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade persistida que representa o setor
 * <p>Dados internos do setor utilizados para gerenciar a classe de persistência</p>
 * */
@Entity
@Table(name = "setores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100, name = "nome")
    private String nome;

    @Column(length = 100, name = "localizacao")
    private String localizacao;
}
