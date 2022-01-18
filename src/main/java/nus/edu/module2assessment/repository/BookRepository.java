package nus.edu.module2assessment.repository;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import nus.edu.module2assessment.models.Book;

@Repository
public class BookRepository {

    @Autowired
    private RedisTemplate<String, Object>template;

    public void save(String key, Book book) {
        template.opsForValue().set(key, book,  10L, TimeUnit.MINUTES);
    }

    public Book findById(String id) {
        return (Book) template.opsForValue().get(id);
    }
    
}
