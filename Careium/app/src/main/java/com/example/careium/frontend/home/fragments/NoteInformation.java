package com.example.careium.frontend.home.fragments;

public class NoteInformation {
    String title, description;
    long createdTime;

    public NoteInformation(String title, String description, long createdTime) {
        this.title = title;
        this.description = description;
        this.createdTime = createdTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
