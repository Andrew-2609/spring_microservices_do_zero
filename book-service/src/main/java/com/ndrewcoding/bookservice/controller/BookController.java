package com.ndrewcoding.bookservice.controller;

import com.ndrewcoding.bookservice.model.Book;
import com.ndrewcoding.bookservice.repository.BookRepository;
import com.ndrewcoding.bookservice.response.Cambio;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("book-service")
public class BookController {

    private final BookRepository bookRepository;
    private final Environment environment;

    public BookController(BookRepository bookRepository, Environment environment) {
        this.bookRepository = bookRepository;
        this.environment = environment;
    }

    @GetMapping("/{id}/{currency}")
    public Book getBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
        Book book = bookRepository.getById(id);
        if (book == null) throw new RuntimeException("Book not Found!");

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        ResponseEntity<Cambio> response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
        Cambio cambio = response.getBody();

        String port = environment.getProperty("local.server.port");

        book.setEnvironment(port);
        book.setPrice(cambio.getConvertedValue());

        return book;
    }

}
