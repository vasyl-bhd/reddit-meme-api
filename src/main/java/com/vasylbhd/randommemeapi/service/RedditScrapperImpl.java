package com.vasylbhd.randommemeapi.service;

import com.vasylbhd.randommemeapi.dto.RedditResponse;
import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.SearchSort;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.pagination.Paginator;
import net.dean.jraw.pagination.SearchPaginator;
import net.dean.jraw.references.SubredditReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;

@Service
public class RedditScrapperImpl implements RedditScrapper {

    private static final List<String> coolSubreddits = List.of(
            "memes",
            "dankmemes",
            "pequelmemes",
            "evangelionmemes",
            "JoJoMemes",
            "ProgrammerHumor",
            "wholesomememes"
    );

    private final RedditClient redditClient;

    public RedditScrapperImpl(RedditClient redditClient) {
        this.redditClient = redditClient;
    }

    @Override
    public List<RedditResponse> getMemes(String subreddit, int limit) {
        return Optional.ofNullable(subreddit)
                .map(sr -> getBySubreddit(subreddit, limit))
                .orElseGet(() -> getByRandomSubreddit(limit));
    }

    private RedditResponse fromSubmission(Submission submission) {
        return new RedditResponse(
                submission.getPermalink(),
                submission.getTitle(),
                submission.getUrl(),
                submission.getAuthor()
        );
    }

    private List<RedditResponse> fromSubmissions(Paginator<Submission> pages, int limit) {
        List<Listing<Submission>> accumulate = pages.accumulate(1);
        return IntStream.range(0, limit)
                .mapToObj(i -> accumulate.get(getRandomIndex(accumulate.size())))
                .filter(not(List::isEmpty))
                .map(list -> list.get(getRandomIndex(list.size())))
                .map(this::fromSubmission)
                .collect(Collectors.toList());
    }

    private List<RedditResponse> getResponse(SubredditReference subredditReference, int limit) {
        SearchPaginator build = subredditReference
                .search()
                .limit(75)
                .query("self:no")
                .syntax(SearchPaginator.QuerySyntax.PLAIN)
                .sorting(SearchSort.HOT)
                .timePeriod(TimePeriod.ALL)
                .build();

        return fromSubmissions(build, limit);
    }

    private List<RedditResponse> getBySubreddit(String subreddit, int limit) {
        return getResponse(getReferenceForSubreddit(subreddit), limit);
    }

    private List<RedditResponse> getByRandomSubreddit(int limit) {
        return getResponse(getSubReferenceForRandomMeme(), limit);
    }

    private SubredditReference getSubReferenceForRandomMeme() {
        return redditClient.subreddits(
                coolSubreddits.get(0),
                coolSubreddits.get(1),
                coolSubreddits.subList(1, coolSubreddits.size()).toArray(new String[0]));
    }

    private SubredditReference getReferenceForSubreddit(String subreddit) {
        return redditClient.subreddit(subreddit);
    }

    private int getRandomIndex(int listSize) {
        return ThreadLocalRandom.current().nextInt(listSize);
    }
}
