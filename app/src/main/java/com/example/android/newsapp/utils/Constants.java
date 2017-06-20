package com.example.android.newsapp.utils;

public class Constants {

    private Constants() {}

    // Used to size the images in the mobile app so they can animate cleanly from list to detail
    public static final int IMAGE_ANIM_MULTIPLIER = 2;

    // Async loader id
    public static final int NEWS_LOADER_ID = 1;

    // Variable holding title of article object to pass it between activities
    public static final String WHOLE_ARTICLE = "article";

    // Max # of news to show at once
    public static final int MAX_NEWS = 14;

    // Guardian open platform
    // News feed base query URL
    public static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";

    // API key
    public static final String API_KEY = "4b7c4cdf-013f-410c-a8a9-654d17207c1a";

    // News feed sections
    public static final String NEWS = "news";
    public static final String TECHNOLOGY = "technology";
    public static final String BUSINESS = "business";
    public static final String TRAVEL = "travel";
    public static final String SPORT = "sport";
}
