package com.yan.architect.architectdemo;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.yan.architect.architectdemo.mapper")
public class ArchitectDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArchitectDemoApplication.class, args);
    }

}
