package com.danhramtsov.java.essentials;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class BioTaskTest {
    private void runTest(String name, String age, String city) {
        String input = name + "\n" + age + "\n" + city + "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        BioTask.main(new String[]{});

        // Учтём, что приглашения вывода через print, а не println
        String expectedOutput =
                "Введите ваше имя: " +
                        "Введите ваш возраст: " +
                        "Введите ваш город: " +
                        "Анкета:" + System.lineSeparator() +
                        "Имя: " + name + System.lineSeparator() +
                        "Возраст: " + age + System.lineSeparator() +
                        "Город: " + city + System.lineSeparator();

        assertEquals(expectedOutput, out.toString());
    }

    @Test
    void testInvalidAgeInput() {
        // age введено как строка "abc"
        String input = "Alice\nage\nBerlin\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Проверяем, что выбрасывается NumberFormatException
        assertThrows(NumberFormatException.class, () -> BioTask.main(new String[]{}));
    }

    @Test
    void testNormalInput() {
        runTest("Alice", "25", "Berlin");
    }

    @Test
    void testEmptyStrings() {
        runTest("", "0", "");
    }

    @Test
    void testSpecialCharacters() {
        runTest("Анна-Мария", "30", "München");
    }

    @Test
    void testMaxAge() {
        runTest("Bob", "120", "New York");
    }

    @Test
    void testMinAge() {
        runTest("Charlie", "1", "Tokyo");
    }

    @ParameterizedTest
    @CsvSource({
            "John, 40, London",
            "Мария, 22, Москва",
            "李雷, 18, 北京"
    })
    void testMultipleCases(String name, String age, String city) {
        runTest(name, age, city);
    }
}
