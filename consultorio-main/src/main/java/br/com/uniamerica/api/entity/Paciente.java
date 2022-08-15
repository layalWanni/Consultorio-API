package br.com.uniamerica.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Eduardo Sganderla
 *
 * @since 1.0.0, 22/03/2022
 * @version 1.0.0
 */
@Entity
@Table(name = "pacientes", schema = "public")
@NoArgsConstructor
public class Paciente extends Pessoa {

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_atendimento", nullable = true)
    private TipoAtendimento tipoAtendimento;

    @Getter @Setter
    @Column(name = "numero_cartao_convenio", nullable = true)
    private String numeroCartaoConvenio;

    @Getter @Setter
    @Column(name = "data_vencimento", nullable = true)
    private String dataVencimento;

    @Getter @Setter
    @ManyToOne
    private Convenio convenio;

}
