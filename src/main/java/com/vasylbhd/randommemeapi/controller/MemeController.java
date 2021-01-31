package com.vasylbhd.randommemeapi.controller;

import com.vasylbhd.randommemeapi.dto.RedditResponse;
import com.vasylbhd.randommemeapi.service.RedditScrapper;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.RequiredArgsConstructor;

@Controller("/api/v1/memes")
@RequiredArgsConstructor
public class MemeController {

    private final RedditScrapper redditScrapper;

    @Get(value = "/random", produces = MediaType.APPLICATION_JSON)
    public RedditResponse getRandom() {
        return redditScrapper.getRandomMeme();
    }
}
