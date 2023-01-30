package com.ssafy.vieweongee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//(scanBasePackages = {"org.springframework.security.core"})
@SpringBootApplication(scanBasePackages = {"org.springframework.security.core"})
public class VieweongeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(VieweongeeApplication.class, args);
    }
}
