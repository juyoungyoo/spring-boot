package com.springboot.springbootdatabase.configure;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class H2ServerConfiguration {
    @Bean
    public Server h2TcpConfiguration() throws Exception {
        return Server.createTcpServer().start();
    }
}
