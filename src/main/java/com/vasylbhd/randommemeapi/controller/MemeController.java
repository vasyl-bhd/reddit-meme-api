package com.vasylbhd.randommemeapi.controller;

import com.vasylbhd.randommemeapi.dto.RedditResponse;
import com.vasylbhd.randommemeapi.service.RedditScrapper;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api/v1/memes")
public class MemeController {

    private static final String ERROR_MESSAGE = "'count' should be between 1 and 25";
    private final RedditScrapper redditScrapper;

    public MemeController(RedditScrapper redditScrapper) {
        this.redditScrapper = redditScrapper;
    }

    @GetMapping(value = "/{subreddit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Validated
    private List<RedditResponse> getBySubreddit(
            @PathVariable String subreddit,
            @Size(min = 1, max = 25) @RequestParam(required = false, defaultValue = "1") byte count) {
        if (count < 1 || count > 25) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return redditScrapper.getMemes(subreddit, count);
    }

    @GetMapping(value = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<RedditResponse> getRandom(
            @Size(min = 1, max = 25) @RequestParam(required = false, defaultValue = "1") byte count) {
        if (count < 1 || count > 25) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return redditScrapper.getMemes(null, count);
    }
}
