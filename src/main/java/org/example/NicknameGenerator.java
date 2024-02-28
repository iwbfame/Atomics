package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NicknameGenerator {
    private static final AtomicInteger beautifulWordsLength3 = new AtomicInteger(0);
    private static final AtomicInteger beautifulWordsLength4 = new AtomicInteger(0);
    private static final AtomicInteger beautifulWordsLength5 = new AtomicInteger(0);

    public static void main(String[] args) {
        String[] texts = new String[100_000];
        Random random = new Random();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    switch (text.length()) {
                        case 3:
                            beautifulWordsLength3.incrementAndGet();
                            break;
                        case 4:
                            beautifulWordsLength4.incrementAndGet();
                            break;
                        case 5:
                            beautifulWordsLength5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        Thread singleLetterThread = new Thread(() -> {
            for (String text : texts) {
                if (isSingleLetter(text)) {
                    switch (text.length()) {
                        case 3:
                            beautifulWordsLength3.incrementAndGet();
                            break;
                        case 4:
                            beautifulWordsLength4.incrementAndGet();
                            break;
                        case 5:
                            beautifulWordsLength5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        Thread increasingLetterThread = new Thread(() -> {
            for (String text : texts) {
                if (isIncreasingLetters(text)) {
                    switch (text.length()) {
                        case 3:
                            beautifulWordsLength3.incrementAndGet();
                            break;
                        case 4:
                            beautifulWordsLength4.incrementAndGet();
                            break;
                        case 5:
                            beautifulWordsLength5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        palindromeThread.start();
        singleLetterThread.start();
        increasingLetterThread.start();

        try {
            palindromeThread.join();
            singleLetterThread.join();
            increasingLetterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + beautifulWordsLength3.get());
        System.out.println("Красивых слов с длиной 4: " + beautifulWordsLength4.get());
        System.out.println("Красивых слов с длиной 5: " + beautifulWordsLength5.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isSingleLetter(String text) {
        char firstChar = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIncreasingLetters(String text) {
        char prevChar = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (currentChar < prevChar) {
                return false;
            }
            prevChar = currentChar;
        }
        return true;
    }
}

