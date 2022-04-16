package com.millimetric.demo.controller;

import com.millimetric.demo.controller.model.CreateBookRequest;
import com.millimetric.demo.service.BookService;
import com.millimetric.demo.service.model.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody CreateBookRequest request) {
        bookService.createBook(request);
    }

    @GetMapping
    public List<BookResponse> getBooks(@RequestParam("page") int page, @RequestParam("size") int size) {
        return bookService.getBooks(page, size);
    }

}
