package org.example.course_project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class message_to_recipients {
    public static String choose_file(String recipient_addresses) {
        StringBuilder temp_messageText = new StringBuilder(); // Используем StringBuilder для эффективности

        try (BufferedReader reader = new BufferedReader(new FileReader(recipient_addresses))) { // try-with-resources
            String line;
            while ((line = reader.readLine()) != null) {
                temp_messageText.append(line).append("\n"); // Добавляем \n после каждой строки
            }
            //System.out.println(temp_messageText);
            String messageText = temp_messageText.toString();
            return messageText;
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка";
        }
    }
}
