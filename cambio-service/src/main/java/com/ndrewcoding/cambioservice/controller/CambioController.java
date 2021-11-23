package com.ndrewcoding.cambioservice.controller;

import com.ndrewcoding.cambioservice.model.Cambio;
import com.ndrewcoding.cambioservice.repository.CambioRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

    private final CambioRepository cambioRepository;
    private final Environment environment;

    public CambioController(CambioRepository cambioRepository, Environment environment) {
        this.cambioRepository = cambioRepository;
        this.environment = environment;
    }

    @GetMapping("/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from, @PathVariable("to") String to) {
        Cambio cambio = cambioRepository.findByFromAndTo(from, to);
        if (cambio == null) throw new RuntimeException("Currency Unsupported!");

        String port = environment.getProperty("local.server.port");
        cambio.setEnvironment(port);

        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));

        return cambio;
    }

}
