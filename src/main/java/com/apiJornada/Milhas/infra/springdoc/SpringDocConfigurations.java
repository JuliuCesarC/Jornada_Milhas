package com.apiJornada.Milhas.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfigurations {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Api Jornada Milhas")
            .version("1.0.0")
            .description(
                "Api Rest para a aplicação Jornada Milhas, contendo as funcionalidades de CRUD e outros que serão utilizados na pagina web.")
            .contact(new Contact()
                .name("Juliu Cesar")
                .email("juliu2012@gmail.com")));
  }

}
