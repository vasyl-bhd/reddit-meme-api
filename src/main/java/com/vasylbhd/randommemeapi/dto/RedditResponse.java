package com.vasylbhd.randommemeapi.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record RedditResponse(
        String postUrl,
        String title,
        String message,
        String author) {
}
