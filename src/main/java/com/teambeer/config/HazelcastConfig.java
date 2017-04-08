package com.teambeer.config;

import com.hazelcast.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@Component
@ConfigurationProperties(prefix = "hazelcast")
public class HazelcastConfig extends Config {
}
