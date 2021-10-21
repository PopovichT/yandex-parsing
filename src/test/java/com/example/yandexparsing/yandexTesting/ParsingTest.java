package com.example.yandexparsing.yandexTesting;


import com.example.yandexparsing.config.Config;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class ParsingTest {

    private final Config config = new Config();

    @Test
    void canParse() {
        var driver = config.webDriver();
    }
}
