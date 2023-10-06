package org.ligot.afriyan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://www.youthfp.cm:8080/")//www.youthfp.cm
        },
                info = @Info(
                title = "AfriYan APIs",
                version = "1.0",
                contact = @Contact(name = "ETS Lingot", email = "youthfp@youthfp.cm", url = "www.youthfp.cm"),
                description = "AfriYan Information",
                license = @License(name = "Licence API")))
@SecurityScheme(name = "auth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OngafriyanApplication {

    public static void main(String[] args) {
        SpringApplication.run(OngafriyanApplication.class, args);
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
