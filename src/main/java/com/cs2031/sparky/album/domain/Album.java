package com.cs2031.sparky.album.domain;

import jakarta.persistence.*;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int year;
    private String coverUrl;

    public Album() {}

    public Album(String title, int year, String coverUrl) {
        this.title = title;
        this.year = year;
        this.coverUrl = coverUrl;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getCoverUrl() { return coverUrl; }
}
