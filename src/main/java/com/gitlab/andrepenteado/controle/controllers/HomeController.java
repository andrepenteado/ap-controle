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

    @RequestMapping("/erro")
    public String erro() {
        return "erro";
    }

    @Override
    public String getErrorPath() {
        return "/erro";
    }

}
