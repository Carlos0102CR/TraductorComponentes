package com.traductor.domain;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="id_user")
    private String User;

    @Column(name="id_name")
    private String idName;

    @Column(name="title")
    private String title;

    @Column(name="url")
    private String url;

    @Column(name="crated_date")
    private String created;

    @Transient
    private MultipartFile pdf;

    public Document() {
    }

    public Document(String user, String idName, String title, String url, String created) {
        User = user;
        this.idName = idName;
        this.title = title;
        this.url = url;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
