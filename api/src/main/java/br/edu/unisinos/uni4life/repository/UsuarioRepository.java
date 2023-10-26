package br.edu.unisinos.uni4life.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.edu.unisinos.uni4life.domain.entity.UsuarioEntity;

public interface UsuarioRepository extends CrudRepository<UsuarioEntity, UUID> {

}
