package com.vasylbhd.randommemeapi.config;

import io.micronaut.context.annotation.Factory;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;

import javax.inject.Singleton;
import java.util.UUID;

@Factory
public class JRawConfig {
    private final RedditConfigProperties properties;

    public JRawConfig(RedditConfigProperties properties) {
        this.properties = properties;
    }

    private UserAgent userAgent() {
        return new UserAgent("memegenerator", "com.vasylbhd.randommemeapi", "v0.1", "henichaer");
    }

    @Singleton
    public RedditClient redditClient() {
        Credentials credentials = Credentials.userless(
                properties.getClientId(),
                properties.getClientSecret(),
                UUID.randomUUID());

        // This is what really sends HTTP requests
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent());

        // Authenticate and get a RedditClient instance
        return OAuthHelper.automatic(adapter, credentials);
    }
}
