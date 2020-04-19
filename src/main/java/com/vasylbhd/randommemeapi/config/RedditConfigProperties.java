package com.vasylbhd.randommemeapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties("reddit")
@Component
@Data
@Validated
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
