package com.example.android.newsapp.ui;

import android.net.Uri;

import java.io.Serializable;

/**
 * A simple news article class to easily pass data around.
 * Serializable interface lets us to pass Article object between activities
 */
@SuppressWarnings("serial")
public class Article implements Serializable {
    public String title;
    public String description;
    public String longDescription;
    public String imageUrl;
    public String datePublished;
    public String webUrl;
    public String sectionName;

    public Article(String title, String description, String longDescription, Uri imageUrl, String datePublished, String webUrl, String sectionName) {
        this.title = title;
        this.description = description;
        this.longDescription = longDescription;
        this.imageUrl = uriToString(imageUrl);
        this.datePublished = datePublished;
        this.webUrl = webUrl;
        this.sectionName = sectionName;
    }

    private String uriToString(Uri uri) {
        if (uri != null) {
            return uri.toString();
        } else {
            return null;
        }
    }

    public Uri getUrl() {
        return Uri.parse(this.webUrl);
    }
}