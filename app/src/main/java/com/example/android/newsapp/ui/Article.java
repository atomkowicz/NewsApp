package com.example.android.newsapp.ui;

import android.net.Uri;

import java.io.Serializable;

/**
 * A simple news article class to easily pass data around.
 * Serializable interface lets us to pass Article object between activities
 */
@SuppressWarnings("serial")
public class Article implements Serializable {
    private String title;
    private String description;
    private String longDescription;
    private String imageUrl;
    private String datePublished;
    private String webUrl;
    private String sectionName;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        title = pTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String pLongDescription) {
        longDescription = pLongDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String pImageUrl) {
        imageUrl = pImageUrl;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String pDatePublished) {
        datePublished = pDatePublished;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String pWebUrl) {
        webUrl = pWebUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String pSectionName) {
        sectionName = pSectionName;
    }
}