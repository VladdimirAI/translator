package com.example.ParserHH;

//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.*;
//import java.util.regex.*;
//
//
//
//public class HhVacancyLinkExtractor {
//    public static void main(String[] args) {
//        try {
//            String filePath = "C:\\ХХ14v1.txt"; // Укажите путь к вашему файлу
//            String content = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
//
//            // Более общее регулярное выражение для поиска ссылок
//            String regex = "https://hh.ru/vacancy/[\\d\\w?&;=]+";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(content);
//
//            while (matcher.find()) {
//                System.out.println(matcher.group());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.regex.*;

public class HhVacancyLinkExtractor {
    public static void main(String[] args) {
        try {
            String filePath = "C:\\ХХ14v1.txt"; // Укажите путь к вашему файлу
            String content = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);

            // Регулярное выражение для поиска ссылок в формате https://hh.ru/vacancy/91486271
            String regex = "https://hh.ru/vacancy/\\d+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                System.out.println(matcher.group());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
