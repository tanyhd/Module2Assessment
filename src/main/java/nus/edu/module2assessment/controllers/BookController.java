package nus.edu.module2assessment.controllers;

import javax.websocket.server.PathParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.support.ModelAndViewContainer;

import nus.edu.module2assessment.services.BookService;

@Controller
public class BookController {

    @Autowired
    BookService bookService;
    
    @GetMapping("/book/{id}")
    public String getMethodName(@PathVariable String id, Model model) {
        model.addAttribute("bookDetails", bookService.searchBookDetail(id));
        return "bookDetails";
    }
    
}
