package com.moviesdata.utils;

import com.opencsv.bean.CsvBindByName;

public
class CSVMovieData {

    @CsvBindByName(column ="Rank")
    private int rank;
    @CsvBindByName(column ="Title")
    private String title;
    @CsvBindByName(column ="Genre")
    private String genres;
    @CsvBindByName(column ="Description")
    private String description;
    @CsvBindByName(column ="Director")
    private String director;
    @CsvBindByName(column ="Actors")
    private String actors;
    @CsvBindByName(column ="Year")
    private int year;
    @CsvBindByName(column ="Runtime (Minutes)")
    private int minutes;
    @CsvBindByName(column ="Rating")
    private float rating;
    @CsvBindByName(column ="Votes")
    private int votes;
    @CsvBindByName(column ="Revenue (Millions)")
    private float millions;
    @CsvBindByName(column = "Metascore")
    private float metascore;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public float getMillions() {
        return millions;
    }

    public void setMillions(float millions) {
        this.millions = millions;
    }

    public float getMetascore() {
        return metascore;
    }

    public void setMetascore(float metascore) {
        this.metascore = metascore;
    }


}