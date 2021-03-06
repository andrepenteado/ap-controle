package com.github.andrepenteado.apcontrole.controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.andrepenteado.apcontrole.entities.Usuario;
import com.github.andrepenteado.apcontrole.entities.enums.Perfil;
import com.github.andrepenteado.apcontrole.repositories.UsuarioRepository;

import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/usuarios")
public class UsuariosSistemaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MessageSource config;

    @RequestMapping
    public String pesquisar(Model model) {
        model.addAttribute("listagemUsuarios", usuarioRepository.findAll(Sort.by(Direction.ASC, "nome")));
        return "usuarios/pesquisar";
    }

    @RequestMapping("/incluir")
    public String incluirUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return cadastroUsuario(model);
    }

    @RequestMapping("/editar/{id}")
    public String editarUsuario(Model model, @PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        model.addAttribute("usuario", usuario);
        return cadastroUsuario(model);
    }

    @RequestMapping("/meusdados")
    public String meusDados(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findUsuarioByLogin(principal.getName());
        model.addAttribute("usuario", usuario);
        return cadastroUsuario(model);
    }

    public String cadastroUsuario(Model model) {
        model.addAttribute("listaPerfis", Perfil.values());
        return "usuarios/cadastro";
    }

    @PostMapping("/gravar")
    public String gravar(Model model, @ModelAttribute("usuario") @Valid Usuario usuario, BindingResult result,
                    @RequestParam(value = "txt_nova_senha") String novaSenha) {
        try {
            if (!result.hasErrors()) {
                if (novaSenha != null && !novaSenha.isEmpty())
                    usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
                Usuario usuarioAtualizado = usuarioRepository.save(usuario);
                log.info(usuarioAtualizado.toString() + " gravado com sucesso");
                model.addAttribute("mensagemInfo", config.getMessage("gravadoSucesso", new Object[] { "o usu??rio" }, null));
            }
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            model.addAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
        return cadastroUsuario(model);
    }

    @RequestMapping("/excluir/{id}")
    public String excluirUsuario(RedirectAttributes ra, @PathVariable Long id) {
        try {
            usuarioRepository.deleteById(id);
            log.info("Usu??rio id #" + id + " exclu??do com sucesso");
            ra.addFlashAttribute("mensagemInfo", config.getMessage("excluidoSucesso", new Object[] { "o usu??rio" }, null));
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
            ra.addFlashAttribute("mensagemErro", config.getMessage("erroProcessamento", null, null));
        }
        return "redirect:/usuarios";
    }
}
