package com.tensquare.encrypt;

/**
 * @author haixin
 * @time 2020/2/15
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class EncryptApplication {
    public static void main(String[] args) {
        SpringApplication.run(EncryptApplication.class);
    }
}