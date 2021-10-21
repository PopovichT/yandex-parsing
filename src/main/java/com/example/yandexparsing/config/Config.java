package com.example.yandexparsing.config;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.Collections;
import java.util.Random;

@Slf4j
@Configuration
public class Config {

    private Random random = new Random();

    @Value("${hhparser.path.driver}")
    public String webDriverPath;

    @Value("${hhparser.path.extensions}")
    public String extensionsPath;

    @Value("${hhparser.path.binary}")
    public String binaryChromePath;

    @Value("#{new Boolean('${hhparser.switcher.proxy}')}")
    public Boolean enableProxy;

    @Value("${hhparser.switcher.proxynumber}")
    public int proxySwitcherNuber;

    @Bean(destroyMethod = "quit")
    @Scope("singleton")
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        options.addArguments("--lang=en-GB");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        options.setBinary(binaryChromePath);
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--disable-infobars"); // disabling infobars
        options.addExtensions(new File(extensionsPath + "all navigator breakout.crx"));

        if (enableProxy) {
            switch (proxySwitcherNuber) {
                case (1): options.addExtensions(new File(extensionsPath + "87.255.10.12_4402_st.lvl.3.crx"));
                case (2): options.addExtensions(new File(extensionsPath + "87.255.10.12_6104_st.lvl.3.crx"));
                case (3): options.addExtensions(new File(extensionsPath + "TestProxyV2.crx"));
                case (4): options.addExtensions(new File(extensionsPath + "87.255.10.12_6103_st.lvl.2.crx"));
                case (5): options.addExtensions(new File(extensionsPath + "87.255.10.12_6101_st.lvl.1.crx"));
            }
        }

        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        ChromeDriver chromeDriver = new ChromeDriver(options);
        return chromeDriver;
    }
}