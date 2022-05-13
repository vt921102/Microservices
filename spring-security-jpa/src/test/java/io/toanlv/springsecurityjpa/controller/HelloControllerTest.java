package io.toanlv.springsecurityjpa.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {

    @BeforeEach
    void setUp() {
        System.out.println("Before");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Before");
    }

    @Test
    void home() {
        Assert.hasText(("<h1>Welcome</h1>"));
    }

    @Test
    void user() {
    }

    @Test
    void admin() {
    }
}