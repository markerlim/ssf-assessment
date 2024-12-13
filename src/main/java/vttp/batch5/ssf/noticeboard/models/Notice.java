package vttp.batch5.ssf.noticeboard.models;

import java.time.LocalDate;
import java.util.List;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vttp.batch5.ssf.noticeboard.validation.ValidList;

public class Notice {
    @NotEmpty
    @Size(min=3, max=128, message = "The length of the title should be between 3 and 128")
    private String title;

    @NotEmpty
    @Email(message = "Invalid email")
    private String poster;

    @NotNull
    @Future(message = "Date must be in the future")
    private LocalDate postDate;

    @ValidList
    @NotEmpty
    private List<String> categories;

    @NotEmpty
    private String text;

    public Notice(){

    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public LocalDate getPostDate() {
        return postDate;
    }
    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Notice [title=" + title + ", poster=" + poster + ", postDate=" + postDate + ", categories=" + categories
                + ", text=" + text + "]";
    }

    
}
