package com.example.demo.service;

import com.example.demo.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong seq = new AtomicLong(1);

    public BookService() {
        // sample data
        save(new Book(null, "TKAYN va 100 creep", "TKAIDO", "2280603117"));
    }

    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    public Optional<Book> findById(Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(seq.getAndIncrement());
            books.add(book);
            return book;
        }
        // update if exists
        return update(book.getId(), book);
    }

    public Book update(Long id, Book book) {
        Optional<Book> existing = findById(id);
        if (existing.isPresent()) {
            Book b = existing.get();
            b.setTitle(book.getTitle());
            b.setAuthor(book.getAuthor());
            b.setIsbn(book.getIsbn());
            return b;
        }
        // if not found, treat as create with provided id
        book.setId(id);
        books.add(book);
        return book;
    }

    public boolean delete(Long id) {
        return books.removeIf(b -> b.getId().equals(id));
    }
}
