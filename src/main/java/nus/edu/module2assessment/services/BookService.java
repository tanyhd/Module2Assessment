package nus.edu.module2assessment.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import nus.edu.module2assessment.models.Book;
import nus.edu.module2assessment.models.BookSearch;
import nus.edu.module2assessment.repository.BookRepository;

@Service
public class BookService {
    
    private final Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    BookRepository bookRepository;

    public List<BookSearch> Search(String title) {
        
        List<BookSearch> bookSearchResult = new ArrayList<>();

        String url = UriComponentsBuilder
                    .fromUriString("http://openlibrary.org/search.json")
                    .queryParam("title", title)
                    .queryParam("limit", "20")
                    .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        if(resp.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error: status code %d".formatted(resp.getStatusCode().toString()));
        }

        String body = resp.getBody();
  
        try(InputStream is = new ByteArrayInputStream(body.getBytes())) {

            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();

            for(int i=0; i <= 20; i++) {
                BookSearch book = new BookSearch();
                book.setTitle(result.getJsonArray("docs").getJsonObject(i).getString("title"));
                book.setWorksId(result.getJsonArray("docs").getJsonObject(i).getString("key").substring(7));
                bookSearchResult.add(book);
            }

        } catch (Exception e) {}
        
        return bookSearchResult;
    }


    public Book searchBookDetail(String id) {

        if(bookRepository.findById(id) != null) {
            Book book = new Book();
            book = bookRepository.findById(id);
            book.setCached(true);
            return book;
        }

        String urlString = "https://openlibrary.org/works/" + id + ".json";
    
        String url = UriComponentsBuilder
                    .fromUriString(urlString)
                    .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        if(resp.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error: status code %d".formatted(resp.getStatusCode().toString()));
        }

        String body = resp.getBody();
        Book book = new Book();
  
        try(InputStream is = new ByteArrayInputStream(body.getBytes())) {

            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();
            book.setId(id);
            book.setCached(false);
            book.setTitle(result.getString("title"));
            
            try {
                book.setDescription(result.getJsonObject("description").getString("value"));
            } catch (Exception e) {}

            try {
                book.setDescription(result.getString("description"));
            } catch (Exception e) {}

            try {
                book.setExcerpt(result.getJsonArray("excerpts").getJsonObject(0).getString("excerpt")); 
            } catch (Exception e) {}
            
            if (book.getDescription() == null)
                book.setDescription("No description available");

            if (book.getExcerpt() == null)
                book.setExcerpt("No excerpt available");

        } catch (Exception e) {}

        bookRepository.save(id, book);
        return book;
    }

}
