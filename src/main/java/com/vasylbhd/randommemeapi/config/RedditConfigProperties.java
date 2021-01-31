package com.vasylbhd.randommemeapi.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties("reddit")
@Data
public class RedditConfigProperties {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String clientId;
    @NotBlank
    private String clientSecret;
}
