package com.vasylbhd.randommemeapi.controller;

import com.vasylbhd.randommemeapi.dto.RedditResponse;
import com.vasylbhd.randommemeapi.service.RedditScrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/memes")
@RequiredArgsConstructor
public class MemeController {

    private final RedditScrapper redditScrapper;

    @GetMapping(value = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
    private RedditResponse getRandom() {
        return redditScrapper.getRandomMeme();
    }
}
