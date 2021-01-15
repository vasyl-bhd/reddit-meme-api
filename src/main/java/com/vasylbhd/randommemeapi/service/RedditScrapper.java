package com.vasylbhd.randommemeapi.service;

import com.vasylbhd.randommemeapi.dto.RedditResponse;

public interface RedditScrapper {
    RedditResponse getRandomMeme();
}
