package com.traductor.domain;


import javax.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_document")
    private Long idDocument;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name="id_name")
    private String idName;

    @Column(name="title")
    private String title;

    @Column(name="url")
    private String url;

    @Column(name="created")
    private LocalDate created;
    public Document() {
    }

    public Document(Long idUser, String idName, String title, String url, LocalDate created) {
        this.idUser = idUser;
        this.idName = idName;
        this.title = title;
        this.url = url;
        this.created = LocalDate.now();
    }

    public Long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}
