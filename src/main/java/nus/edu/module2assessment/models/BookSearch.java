package nus.edu.module2assessment.models;

import java.io.Serializable;

public class BookSearch implements Serializable{
    
    String worksId;
    String title;

    public String getWorksId() {
        return worksId;
    }
    public void setWorksId(String worksId) {
        this.worksId = worksId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    } 
}
