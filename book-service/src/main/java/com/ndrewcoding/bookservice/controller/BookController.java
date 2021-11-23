package com.ndrewcoding.bookservice.controller;

import com.ndrewcoding.bookservice.model.Book;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("book-service")
public class BookController {

    private final Environment environment;

    public BookController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/{id}/{currency}")
    public Book getBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
        String port = environment.getProperty("local.server.port");

        return new Book(id, "Andrew Schneider", "Deutschland und Brasilien", new Date(), 12.2, currency, port);
    }

}
