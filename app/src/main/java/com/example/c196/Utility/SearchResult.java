package com.example.c196.Utility;

import java.io.Serializable;

public class SearchResult implements Serializable {
    private String type;
    private String title;
    private int id;

    public SearchResult(String type, String title, int id) {
        this.type = type;
        this.title = title;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}

