package com.techacademy;

import java.util.TimeZone;
import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DailyReportSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyReportSystemApplication.class, args);
    }

    @PostConstruct
    public void init(){
      TimeZone.setDefault(TimeZone.getTimeZone("JST"));
    }

}
