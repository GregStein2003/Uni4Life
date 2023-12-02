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
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "COMENTARIOS")
public class ComentarioEntity implements Serializable {

    private static final String GENERATOR_NAME = "UUID";

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = GENERATOR_NAME)
    @GenericGenerator(name = GENERATOR_NAME, strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID_COMENTARIOS", nullable = false)
    private UUID id;

    @Column(name = "TITULO", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false)
    private UsuarioEntity autor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CONTEUDO", referencedColumnName = "ID_CONTEUDO", nullable = false)
    private ConteudoEnitity conteudo;

    @PrePersist
    public void preCreate() {
        this.dataCriacao = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

}
