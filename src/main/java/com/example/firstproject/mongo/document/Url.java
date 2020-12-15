package com.example.firstproject.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document("Url")
public class Url {
    public static final String OriginalUrl_Col = "originalUrl";
    public static final String ShortUrl_Col = "shortUrl";
    public static final String CreationDate_Col = "creationDate";

    @Id
    private String id;

    @Field(OriginalUrl_Col)
    @Indexed(unique = true)
    private String originalUrl;

    @Field(ShortUrl_Col)
    @Indexed(unique = true)
    private String shortUrl;

    @Field(CreationDate_Col)
    private Date creationDate;

    public Url(String originalUrl, String shortUrl, Date creationDate) {
        setShortUrl(shortUrl);
        setOriginalUrl(originalUrl);
        setCreationDate(creationDate);
    }

    public String getId() {
        return id;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "URL{" + "id=" + this.id + ", originalUrl=" + this.originalUrl + '\'' + ", shortUrl='" + this.shortUrl + '\'' + '}';
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}