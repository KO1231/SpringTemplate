package jp.kazutech.springtemplate;

import java.time.ZoneId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTemplateApplication.class, args);
    }

    @Bean
    public ZoneId zoneId() {
        return ZoneId.of("Asia/Tokyo");
    }
}
