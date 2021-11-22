package com.ndrewcoding.cambioservice.controller;

import com.ndrewcoding.cambioservice.model.Cambio;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

    private final Environment environment;

    public CambioController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from, @PathVariable("to") String to) {
        String port = environment.getProperty("local.server.port");

        return new Cambio(1L, from, to, BigDecimal.ONE, BigDecimal.ONE, port);
    }

}
