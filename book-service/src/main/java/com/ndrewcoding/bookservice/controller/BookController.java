package com.ndrewcoding.bookservice.controller;

import com.ndrewcoding.bookservice.model.Book;
import com.ndrewcoding.bookservice.repository.BookRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        String port = environment.getProperty("local.server.port");
        book.setEnvironment(port);

        return book;
    }

}
