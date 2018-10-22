package com.example.demo.moneyTab;

/**
 * Created by anju on 22-10-2018.
 */

public class JSONData {

    private String title;
    private String source;
    private String description;

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public JSONData(String title, String source, String description) {
        this.title = title;
        this.source = source;
        this.description = description;
    }
}
