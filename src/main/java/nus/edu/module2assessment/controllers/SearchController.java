package nus.edu.module2assessment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nus.edu.module2assessment.services.BookService;

@Controller
public class SearchController {

    @Autowired
    BookService bookService;
    
    @GetMapping("/book")
    public String getBook(@RequestParam(required = true) String title, Model model) {
        String titleTrim = title.trim().replaceAll(" ", "+");
        model.addAttribute("titleName", title);
        model.addAttribute("BookIdList", bookService.Search(titleTrim));
        return "result";
    }
}
