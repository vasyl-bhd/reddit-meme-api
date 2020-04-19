package com.vasylbhd.randommemeapi.service;

import com.vasylbhd.randommemeapi.dto.RedditResponse;

import java.util.List;

public interface RedditScrapper {
    List<RedditResponse> getMemes(String subreddit, int limit);
}
