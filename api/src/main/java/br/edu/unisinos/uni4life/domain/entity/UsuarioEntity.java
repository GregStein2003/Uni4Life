package br.edu.unisinos.uni4life.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import br.edu.unisinos.uni4life.domain.enumeration.usuario.Segmento;
import br.edu.unisinos.uni4life.domain.enumeration.usuario.TipoConta;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "USUARIOS")
public class UsuarioEntity implements Serializable {

    private static final String GENERATOR_NAME = "UUID";

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = GENERATOR_NAME)
    @GenericGenerator(name = GENERATOR_NAME, strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID_USUARIOS", nullable = false)
    private UUID id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "SENHA", nullable = false)
    private String senha;

    @Column(name = "REGISTRO_ACADEMICO", nullable = false)
    private String registroAcademico;

    @Column(name = "TELEFONE", nullable = false)
    private String telefone;

    @Column(name = "CEP", nullable = false)
    private String cep;

    @Column(name = "DATA_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_CONTA", nullable = false)
    private TipoConta tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEGMENTO", nullable = false)
    private Segmento segmento;
}
