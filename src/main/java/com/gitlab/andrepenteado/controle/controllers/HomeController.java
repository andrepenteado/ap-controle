package com.gitlab.andrepenteado.controle.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController implements ErrorController {

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/home/secured")
    public String homeSecured() {
        return "home";
    }

    @RequestMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }

    @RequestMapping("/erro")
    public String erro() {
        return "erro";
    }

    @Override
    public String getErrorPath() {
        return "/erro";
    }

}
