package com.example.alankar63.todoapp;


public class Movie {
    private String title, date_time, pending;

    public Movie() {
    }

    public Movie(String title, String date_time, String pending) {
        this.title = title;
        this.date_time = date_time;
        this.pending = pending;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getpending() {
        return pending;
    }

    public void setpending(String pending) {
        this.pending = pending;
    }

    public String getdate_time() {
        return date_time;
    }

    public void setdate_time(String date_time) {
        this.date_time = date_time;
    }
}