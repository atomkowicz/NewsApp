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
    public String secondaryImageUrl = "https://lh4.googleusercontent.com/-ch9Kk-7pD68/VGLkCNh5niI/AAAAAAAAADc/ztxkRHWX-po/w600-no/DSC_2739.JPG";

    public Article(String title, String description, String longDescription, Uri imageUrl,
                   Uri secondaryImageUrl) {
        this.title = title;
        this.description = description;
        this.longDescription = longDescription;
        this.imageUrl = uriToString(imageUrl);
        //this.secondaryImageUrl = uriToString(secondaryImageUrl);
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", imageUrl=" + imageUrl +
                ", secondaryImageUrl=" + secondaryImageUrl +
                '}';
    }


    private String uriToString(Uri uri) {
        return uri.toString();
    }

    private Uri stringToUri(String stringUri) {
        return Uri.parse(stringUri);
    }
}