package com.yangmao.yangoj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true,proxyTargetClass = true)
public class YangOjApplication {

    public static void main(String[] args) {
        SpringApplication.run(YangOjApplication.class, args);
    }

}
