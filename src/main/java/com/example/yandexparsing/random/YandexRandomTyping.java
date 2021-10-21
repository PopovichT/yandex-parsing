package com.example.yandexparsing.random;

import org.springframework.stereotype.Service;

import java.util.Random;

import static java.lang.Math.abs;

@Service
public class YandexRandomTyping {
    private final Random random;

    public YandexRandomTyping() {
        this.random = new Random();
    }

    public long getRandomTypingInterval() {
        var interval = Math.round(abs(random.nextGaussian() * 200) + 200);
        return interval;
    }
}
