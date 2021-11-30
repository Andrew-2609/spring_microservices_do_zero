package com.ndrewcoding.bookservice.controller;

import com.ndrewcoding.bookservice.model.Book;
import com.ndrewcoding.bookservice.proxy.CambioProxy;
import com.ndrewcoding.bookservice.repository.BookRepository;
import com.ndrewcoding.bookservice.response.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book Endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {

    private final BookRepository bookRepository;
    private final CambioProxy cambioProxy;
    private final Environment environment;

    public BookController(BookRepository bookRepository, CambioProxy cambioProxy, Environment environment) {
        this.bookRepository = bookRepository;
        this.cambioProxy = cambioProxy;
        this.environment = environment;
    }

    @Operation(summary = "Returns a specific book by its ID")
    @GetMapping("/{id}/{currency}")
    public Book getBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not Found!"));

        Cambio cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);

        String port = environment.getProperty("local.server.port");

        book.setEnvironment("Book-Service Port: " + port + " - Cambio-Service Port: " + cambio.getEnvironment());
        book.setPrice(cambio.getConvertedValue());

        return book;
    }

}
