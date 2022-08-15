package br.com.uniamerica.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Eduardo Sganderla
 *
 * @since 1.0.0, 22/03/2022
 * @version 1.0.0
 */
@Entity
@Table(name = "historicos", schema = "public")
@NoArgsConstructor
public class Historico extends AbstractEntity {

    @Getter @Setter
    @Column(name = "observacao", nullable = false, unique = true, length = 50)
    private String observacao;

    @Getter @Setter
    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAgenda statusAgenda;

    @Getter @Setter
    @JoinColumn(name = "id_secretaria")
    @ManyToOne(fetch = FetchType.EAGER)
    private Secretaria secretaria;

    @Getter @Setter
    @ManyToOne
    private Paciente paciente;

    @Getter @Setter
    @ManyToOne
    private Agenda agenda;

}
