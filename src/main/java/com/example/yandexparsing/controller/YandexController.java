package com.example.yandexparsing.controller;

import com.example.yandexparsing.random.YandexRandomTyping;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping()
public class YandexController {

    private final WebDriver driver;
    private final YandexRandomTyping typing;

    @Autowired
    public YandexController(WebDriver driver, YandexRandomTyping typing) {
        this.driver = driver;
        this.typing = typing;
    }

    @RequestMapping("get")
    public void getTop20(@RequestParam String phrase) throws InterruptedException {

        List<String> listOfStrings = new ArrayList<>();
        driver.manage().window().maximize();
        driver.get("https://yandex.ru/");
        var js = (JavascriptExecutor) driver;
        log.info("word={} search started", phrase);
        Thread.sleep(2000);

        while (true) {
            var yandexForm = driver.findElement(By.cssSelector("input[class*='input__control']"));
            yandexForm.clear();
            typeWord(yandexForm, phrase);
            WebElement yandexSearchButton;
            try {
                yandexSearchButton = driver.findElement(By.cssSelector("div[class*='websearch-button__text']"));
            }
            catch (NoSuchElementException e) {
                yandexSearchButton = driver.findElement(By.cssSelector("button[class*='button_theme_search']"));
            }

            actionClick(yandexSearchButton);

            Thread.sleep(2000);
            var list = driver.findElements(By.cssSelector("div[class*='Organic-Path']"));
            list.forEach(p -> listOfStrings.add(p.getText()));
            var next = driver.findElement(By.cssSelector("a[aria-label='Следующая страница']"));
            js.executeScript("arguments[0].scrollIntoView(true);", next);

            actionClick(next);
            Thread.sleep(2000);
            var list2 = driver.findElements(By.cssSelector("div[class*='Organic-Path']"));
            list2.forEach(p -> listOfStrings.add(p.getText()));

            js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("input[class*='input__control']")));
        }
    }

    private void typeWord(WebElement element, String text) throws InterruptedException {
        log.info("Typing text={} into searching form", text);

        for (int i = 0; i < text.length(); i++) {

            Thread.sleep(typing.getRandomTypingInterval());
            String s = String.valueOf(text.charAt(i));
            element.sendKeys(s);
        }
    }

    private void actionClick(WebElement button) {
        var searchClick = new Actions(driver)
                .moveToElement(button, 15, 15)
                .click()
                .build();
        searchClick.perform();

        log.info("mouse clicked");
    }
}
