package jp.ac.kagawalab.mynah.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "jp.ac.kagawalab.mynah")
@EnableJpaRepositories(basePackages = "jp.ac.kagawalab.mynah.core.repository")
@EntityScan("jp.ac.kagawalab.mynah.core.entity")
public class MynahApplication {

    public static void main(String[] args) {
        SpringApplication.run(MynahApplication.class, args);
    }

}
