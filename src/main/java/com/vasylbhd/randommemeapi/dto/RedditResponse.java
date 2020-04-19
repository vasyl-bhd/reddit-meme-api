package com.vasylbhd.randommemeapi.dto;

import lombok.Value;


@Value
public class RedditResponse {
    String postUrl;
    String title;
    String message;
    String author;
}
