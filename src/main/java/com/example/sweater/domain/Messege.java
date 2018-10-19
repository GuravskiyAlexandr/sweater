package com.example.sweater.domain;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Messege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill message")
    @Length(max = 2048, message = "Messege too long")
    private String text;
    @NotBlank(message = "Please fill message")
    @Length(max = 10, message = "Tag too long")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Messege() {
    }

    public Messege(String text, String tag, User user){
        this.author = user;
        this.text = text;
        this.tag = tag;
    }
    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }
    public User getAuthor() {
        if(author != null)
        return author;
        author = new User();
        author.setUsername("<none>");
        return author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
