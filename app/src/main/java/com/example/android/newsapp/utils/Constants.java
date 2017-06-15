/*
 * Copyright 2015 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.newsapp.utils;

public class Constants {

    private Constants() {};

    // Used to size the images in the mobile app so they can animate cleanly from list to detail
    public static final int IMAGE_ANIM_MULTIPLIER = 2;

    // Async loader id
    public static final int NEWS_LOADER_ID = 1;

    // Variable holding title of article object to pass it between activities
    public static final String WHOLE_ARTICLE = "article";

    // Max # of news to show at once
    public static final int MAX_NEWS = 4;

    // Guardian open platform

    // News feed base query URL
    public static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";

    // API key
    public  static final String API_KEY = "4b7c4cdf-013f-410c-a8a9-654d17207c1a";

    // News feed sections
    public  static final String NEWS = "news";
    public  static final String TECHNOLOGY = "technology";
    public  static final String BUSINESS = "business";
    public  static final String TRAVEL = "travel";
    public  static final String SPORT = "sport";

}
