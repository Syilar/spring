package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


@ComponentScan("org.example")
@Configuration
@PropertySource("classpath:application-init.properties")
@Profile("init")
public class InitAppConfig {
}
