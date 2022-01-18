package nus.edu.module2assessment.services;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import nus.edu.module2assessment.models.BookSearch;

@Service
public class BookService {
    
    private final Logger logger = Logger.getLogger(BookService.class.getName());

    public List<BookSearch> Search(String title) {
        
        List<BookSearch> bookSearchResult = new ArrayList<>();

        String url = UriComponentsBuilder
                    .fromUriString("http://openlibrary.org/search.json")
                    .queryParam("title", title)
                    .queryParam("availability&limit", 20)
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


}
