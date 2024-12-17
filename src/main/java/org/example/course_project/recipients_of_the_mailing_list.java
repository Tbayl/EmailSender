package org.example.course_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class recipients_of_the_mailing_list {

    // Объявляем списки вне метода choose_file
    public static List<String> list_of_adress = new ArrayList<>();
    public static List<String> list_of_names = new ArrayList<>();


    public static String choose_file(String adress_from_sent) {
        try (BufferedReader reader = new BufferedReader(new FileReader(adress_from_sent))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String temp_adress = "";
                String temp_name = "";
                int spaceIndex = line.indexOf(' '); // Находим индекс первого пробела

                if (spaceIndex != -1) { // Проверяем, есть ли пробел
                    temp_adress = line.substring(0, spaceIndex);
                    temp_name = line.substring(spaceIndex + 1).trim(); // Удаляем лишние пробелы в конце
                } else {
                    temp_adress = line; // Если пробела нет, все считаем за адрес
                }


                if (!temp_adress.isEmpty() && (temp_adress.contains("@gmail.com") || temp_adress.contains("@mail.ru") || temp_adress.contains("@vk.com") || temp_adress.contains("@yandex.ru"))) {
                    list_of_adress.add(temp_adress);
                    //System.out.println(temp_adress);
                }
                if (!temp_name.isEmpty()) {
                    list_of_names.add(temp_name);
                }
            }
            return "Успешно";
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка";
        }
    }
}

