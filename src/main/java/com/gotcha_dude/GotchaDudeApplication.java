package com.gotcha_dude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // JPA Auditing 활성화, 시간 자동 변경 기능 활성화 시킴.
public class GotchaDudeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GotchaDudeApplication.class, args);
    }

}
