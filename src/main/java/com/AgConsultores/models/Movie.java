package com.AgConsultores.models;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    protected String title;
    protected int year;
    protected int votes;
    private double rating;
    @Column(name = "image_url")
    protected String imageUrl;

}
