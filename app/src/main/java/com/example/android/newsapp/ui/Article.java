package com.example.android.newsapp.ui;

import android.net.Uri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple news article class to easily pass data around.
 */
@SuppressWarnings("serial")
public class Article {
    private String title;
    private String trailText;
    private String imageUrl;
    private Date datePublished;
    private String webUrl;
    private String sectionName;

    public Article(String title, String trailText, Uri imageUrl, String datePublished, String webUrl,
                   String sectionName) {
        this.title = title;
        this.trailText = trailText;
        this.imageUrl = uriToString(imageUrl);
        this.datePublished = stringToDate(datePublished);
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

    public Uri getWebUrl() {
        return Uri.parse(this.webUrl);
    }

    public String getTitle() {
        return title;
    }

    public String getTrailText() {
        return trailText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDatePublished() {
        return DateFormat.getDateInstance(DateFormat.LONG).format(this.datePublished);
    }

    private Date stringToDate(String datePublished) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(datePublished);
        } catch (java.text.ParseException pE) {
            pE.printStackTrace();
        }
        return convertedDate;
    }
}