package com.example.demo.controller;


import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks()
    {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookId(@PathVariable int id)
    {
        return bookService.GetBookById(id);
    }

    @PostMapping
    public  String addBook(@RequestBody Book book)
    {
        bookService.addBook(book);
        return "Book add Success";
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id,@RequestBody Book updateBook)
    {
        bookService.updateBook(id, updateBook);
        return "Book update Success";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id)
    {
        bookService.deleteBook(id);
        return "Book delete Success";
    }

}
