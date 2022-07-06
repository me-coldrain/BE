package me.coldrain.ninetyminute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NinetyMinuteApplication {

    public static void main(String[] args) {
        SpringApplication.run(NinetyMinuteApplication.class, args);
    }

}
