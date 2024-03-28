package edu.java.bot.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "Bot API",
        version = "1.0.0",
        contact = @Contact(
            name = "Eduard Ivanushkin",
            url = "https://github.com/EduardRK/Tinkoff_Java_Second_Sem"
        )
    )
)
public class OpenApiConfiguration {
    public OpenApiConfiguration() {

    }
}
