package com.bobbbaich.fb.bot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.twitter.api.StreamingOperations;
import org.springframework.social.twitter.api.Twitter;

@Configuration
public class TwitterConfig {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterConfig.class);

    private final Twitter twitter;

    @Autowired
    public TwitterConfig(Twitter twitter) {
        this.twitter = twitter;
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public StreamingOperations streamingOperations() {
        return twitter.streamingOperations();
    }
}
