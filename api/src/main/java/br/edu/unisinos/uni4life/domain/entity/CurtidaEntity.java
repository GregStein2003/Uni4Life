package br.edu.unisinos.uni4life.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "GOSTEIS")
public class CurtidaEntity implements Serializable {

    private static final long serialVersionUID = 2637398695795458452L;

    private static final String GENERATOR_NAME = "UUID";

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = GENERATOR_NAME)
    @GenericGenerator(name = GENERATOR_NAME, strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID_GOSTEI", nullable = false)
    private UUID id;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CONTEUDO", referencedColumnName = "ID_CONTEUDO", nullable = false)
    private ConteudoEnitity conteudo;


    @PrePersist
    public void preCreate() {
        this.dataCriacao = LocalDateTime.now();
    }

}
