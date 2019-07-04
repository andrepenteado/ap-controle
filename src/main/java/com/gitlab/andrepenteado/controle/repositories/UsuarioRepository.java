package com.gitlab.andrepenteado.controle.repositories;

import com.gitlab.andrepenteado.controle.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Usuario findUsuarioByLogin(String login);

}
