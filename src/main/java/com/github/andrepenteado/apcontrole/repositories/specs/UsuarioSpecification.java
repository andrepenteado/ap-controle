package com.github.andrepenteado.apcontrole.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import com.github.andrepenteado.apcontrole.entities.Usuario;

public final class UsuarioSpecification {

    public static Specification<Usuario> igualLogin(String login) {
        return (usuario, cq, cb) -> cb.equal(usuario.get("login"), login);
    }

    public static  Specification<Usuario> contemNome(String nome) {
        return (usuario, cq, cb) -> cb.like(cb.lower(usuario.get("nome")), "%" + nome.toLowerCase() + "%");
    }
}
