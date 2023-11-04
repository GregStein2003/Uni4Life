package br.edu.unisinos.uni4life.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.proxy.HibernateProxy;

import br.edu.unisinos.uni4life.domain.enumeration.usuario.Segmento;
import br.edu.unisinos.uni4life.domain.enumeration.usuario.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "USUARIOS")
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity implements Serializable {

    private static final long serialVersionUID = -400085195788052650L;

    private static final String GENERATOR_NAME = "UUID";

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = GENERATOR_NAME)
    @GenericGenerator(name = GENERATOR_NAME, strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID_USUARIO", nullable = false)
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

    @Column(name = "DATA_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_CONTA", nullable = false)
    private TipoConta tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEGMENTO", nullable = false)
    private Segmento segmento;

    @Column(name = "QUANTIADE_SEGUIDROES", nullable = false)
    private Long quantidadeSeguidores;

		@Column(name = "IMAGEM_USUARIO")
    private String imagem;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<ConteudoEnitity> conteudos;

    @PrePersist
    public void create() {
        this.quantidadeSeguidores = 0L;
    }


    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        final Class<?> oEffectiveClass = o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        final Class<?> thisEffectiveClass = this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        final UsuarioEntity entity = (UsuarioEntity) o;
        return getId() != null && Objects.equals(getId(), entity.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
            .getPersistentClass()
            .hashCode() : getClass().hashCode();
    }
}
