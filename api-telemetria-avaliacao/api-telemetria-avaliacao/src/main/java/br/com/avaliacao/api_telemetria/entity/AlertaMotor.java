package br.com.avaliacao.api_telemetria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidade persistida que representa o alerta do motor
 * <p>Dados internos de AlertaMotor utilizados para gerenciar a classe de persistência</p>
 * */
@Entity
@Table(name = "alertas_motores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertaMotor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motor_id", nullable = false)
    private Motor motor;

    @Column(name = "data_alerta")
    private LocalDateTime dataAlerta;

    @Column(name = "tipo_anomalia", length = 100)
    private String tipoAnomalia;

    @Column(length = 20, name = "criticidade")
    private String criticidade;

    @Column(columnDefinition = "TEXT", name = "descricao")
    private String descricao;

    @Builder.Default
    @Column(name = "resolvido")
    private Boolean resolvido = false;

    @PrePersist
    public void prePersist() {
        if (this.dataAlerta == null) {
            this.dataAlerta = LocalDateTime.now();
        }
    }
}
