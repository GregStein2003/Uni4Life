package br.edu.unisinos.uni4life.domain.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "SEGUIDORES")
public class SeguidorEntity implements Serializable {

    private static final long serialVersionUID = 7420111432239221702L;

    private static final String GENERATOR_NAME = "UUID";

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = GENERATOR_NAME)
    @GenericGenerator(name = GENERATOR_NAME, strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID_SEGUIDO_SEGUIDOR", nullable = false)
    private UUID id;

    @Column(name = "DATA_INICIO_RELACIONAMENTO", nullable = false)
    private LocalDateTime dataInicioRelacionamento;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ID_SEGUIDO", referencedColumnName = "ID_USUARIO", nullable = false)
    private UsuarioEntity seguido;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ID_SEGUIDOR", referencedColumnName = "ID_USUARIO", nullable = false)
    private UsuarioEntity seguidor;

    @PrePersist
    public void preCreate() {
        this.dataInicioRelacionamento = LocalDateTime.now();
    }

}
