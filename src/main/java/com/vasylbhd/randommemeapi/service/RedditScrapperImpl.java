package com.vasylbhd.randommemeapi.service;

import com.vasylbhd.randommemeapi.dto.RedditResponse;
import lombok.RequiredArgsConstructor;
import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.SearchSort;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.pagination.Paginator;
import net.dean.jraw.pagination.SearchPaginator;
import net.dean.jraw.references.SubredditReference;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class RedditScrapperImpl implements RedditScrapper {

    private static final List<String> COOL_SUBREDDITS = List.of(
            "memes",
            "dankmemes",
            "PrequelMemes",
            "evangelionmemes",
            "JoJoMemes",
            "ProgrammerHumor",
            "wholesomememes"
    );

    private final RedditClient redditClient;

    @Override
    public RedditResponse getRandomMeme() {
        return getByRandomSubreddit();
    }

    private RedditResponse getByRandomSubreddit() {
        return getResponse(getSubreddit());
    }

    private SubredditReference getSubreddit() {
        return redditClient.subreddit(COOL_SUBREDDITS.get(getRandomIndex(COOL_SUBREDDITS.size())));
    }

    private RedditResponse getResponse(SubredditReference subredditReference) {
        SearchPaginator paginator = subredditReference
                .search()
                .limit(100)
                .query("self:no")
                .syntax(SearchPaginator.QuerySyntax.PLAIN)
                .sorting(SearchSort.HOT)
                .timePeriod(TimePeriod.MONTH)
                .build();

        return getFromPage(paginator);
    }

    private RedditResponse getFromPage(Paginator<Submission> pages) {
        Listing<Submission> submissions = pages.next();
        return getRedditResponse(submissions);
    }

    @NotNull
    private RedditResponse getRedditResponse(Listing<Submission> next) {
        Submission submission = next.get(getRandomIndex(next.size()));

        return submissionToRedditResponse(submission);
    }

    private RedditResponse submissionToRedditResponse(Submission submission) {
        return new RedditResponse(
                submission.getPermalink(),
                submission.getTitle(),
                submission.getUrl(),
                submission.getAuthor()
        );
    }

    private int getRandomIndex(int listSize) {
        return ThreadLocalRandom.current().nextInt(listSize);
    }
}
