package com.example.ParserHH;

import java.io.*;
import java.util.regex.*;

public class ExtractHHLinks {
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\ХХ14.txt"; // Замените на путь к вашему файлу
        File file = new File(filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Pattern pattern = Pattern.compile("https://hh.ru/vacancy/\\d+\\?query=Java+backend&amp;hhtmFrom=vacancy_search_list");

        while ((line = br.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String url = matcher.group().replace("&amp;", "&");
                System.out.println(url);
            }
        }

        br.close();
    }
}
