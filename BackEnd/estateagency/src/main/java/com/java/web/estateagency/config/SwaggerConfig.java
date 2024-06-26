// package com.java.web.estateagency.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @Configuration
// @EnableSwagger2
// public class SwaggerConfig {
//     @Bean
//     public Docket api() {
//         return new Docket(DocumentationType.SWAGGER_2)
//                 .select()
//                 .apis(RequestHandlerSelectors.basePackage("com.hendisantika.springboot.swagger"))
//                 .paths(PathSelectors.any())
//                 .build().apiInfo(apiInfo());
//     }

//     private ApiInfo apiInfo() {
//         ApiInfo apiInfo = new ApiInfo(
//                 "StudentSearchApplication",
//                 "An application to search Student from a Student repository by studentId",
//                 "StudentSearchApplication v1",
//                 "Terms of service",
//                 "hendisantika@gmail.com",
//                 "License of API",
//                 "https://swagger.io/docs/");
//         return apiInfo;
//     }
// }
