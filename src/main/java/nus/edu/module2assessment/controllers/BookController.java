package nus.edu.module2assessment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import nus.edu.module2assessment.models.Book;
import nus.edu.module2assessment.services.BookService;

@Controller
public class BookController {

    @Autowired
    BookService bookService;
    
    @GetMapping("/book/{id}")
    public String getMethodName(@PathVariable String id, Model model) {
        Book book = new Book();
        book = bookService.searchBookDetail(id);
        model.addAttribute("bookDetails", book);
        model.addAttribute("coverUrl", book.getBookCoverNum());
        return "bookDetails";
    }
    
}
