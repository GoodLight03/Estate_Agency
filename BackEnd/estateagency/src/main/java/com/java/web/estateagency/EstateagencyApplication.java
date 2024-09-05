package com.java.web.estateagency;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Estateagency REST API Documentation",
                description = "Estateagency REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Nguyen Quang",
                        email = "nguyenvanphoc20@gmail.com",
                        url = "https://www.facebook.com/?locale=vi_VN"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.facebook.com/?locale=vi_VN"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description =  "Estateagency REST API Documentation",
                url = "https://www.facebook.com/?locale=vi_VN"
        )
)
public class EstateagencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstateagencyApplication.class, args);
    }

}
