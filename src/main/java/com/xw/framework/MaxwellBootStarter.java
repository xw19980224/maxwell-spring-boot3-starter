package com.xw.framework;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class MaxwellBootStarter extends AbstractBootStarter {

    private static final Logger log = LoggerFactory.getLogger(MaxwellBootStarter.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MaxwellBootStarter.class, args);
        log.info("\n\nSwagger-UI: http://localhost:{}/doc.html\n\n", context.getEnvironment().getProperty("server" +
                ".port"));
    }

}
