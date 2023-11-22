package pro.sky.animalizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnimalizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalizerApplication.class, args);
    }
}