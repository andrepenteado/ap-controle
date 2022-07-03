package com.github.andrepenteado.apcontrole.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.andrepenteado.apcontrole.repositories.UsuarioRepository;
import com.github.andrepenteado.apcontrole.repositories.specs.UsuarioSpecification;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void pesquisarPorLogin() {
        assertFalse(usuarioRepository.findAll(UsuarioSpecification.igualLogin("admin")).isEmpty());
        assertTrue(usuarioRepository.findAll(UsuarioSpecification.igualLogin("não-existe")).isEmpty());
    }

    @Test
    public void pesquisarPorNome() {
        assertFalse(usuarioRepository.findAll(UsuarioSpecification.contemNome("inistr")).isEmpty());
        assertTrue(usuarioRepository.findAll(UsuarioSpecification.contemNome("Não existe")).isEmpty());
    }
}
