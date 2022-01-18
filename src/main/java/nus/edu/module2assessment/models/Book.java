package nus.edu.module2assessment.models;

import java.io.Serializable;

public class Book implements Serializable {
    
    private String title;
    private String id;
    private String description;
    private String excerpt;
    private boolean cached;
    private String bookCoverNum;

    public String getBookCoverNum() {
        return bookCoverNum;
    }
    public void setBookCoverNum(String bookCoverNum) {
        this.bookCoverNum = bookCoverNum;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getExcerpt() {
        return excerpt;
    }
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
    public boolean isCached() {
        return cached;
    }
    public void setCached(boolean cached) {
        this.cached = cached;
    }

    
}
