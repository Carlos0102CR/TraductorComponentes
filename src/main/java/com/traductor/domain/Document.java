package com.traductor.domain;


import javax.persistence.*;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="id_user")
    private Long idUser;

    @Column(name="id_name")
    private String idName;

    @Column(name="title")
    private String title;

    @Column(name="url")
    private String url;

    @Column(name="crated_date")
    private String created;
    public Document() {
    }

    public Document(Long idUser, String idName, String title, String url, String created) {
        this.idUser = idUser;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
