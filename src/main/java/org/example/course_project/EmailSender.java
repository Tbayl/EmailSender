package org.example.course_project;

import org.example.course_project.message_to_recipients;
import sample.HelloController;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;



public class EmailSender {
    static public boolean flagA;

    // Метод для отправки письма
    public static void sendEmail(String from, String password, List<String> recipients, String subject, String messageText) {
        // Настройка свойств почтового сервера
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.yandex.ru");  // Адрес SMTP-сервера Яндекса
        properties.put("mail.smtp.port", "465");  // Порт для SSL
        properties.put("mail.smtp.auth", "true");  // Включаем авторизацию
        properties.put("mail.smtp.enable", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.debug", "true");


        // Создание сессии с аутентификацией
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Перебор получателей и отправка им персонализированных писем
            int i = 0;
            Flags flags = new Flags();
            for (String recipient : recipients) {
                MimeMessage message = new MimeMessage(session);

                // Устанавливаем адрес отправителя
                message.setFrom(new InternetAddress(from));

                // Устанавливаем адрес получателя
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

                // Устанавливаем тему письма
                message.setSubject(subject);
                String[] array = recipients_of_the_mailing_list.list_of_names.toArray(new String[0]); // Преобразование в массив
                // Персонализируем сообщение для каждого получателя
                String personalizedMessage = "Здравствуйте, " + array[i] + "!\n\n" + messageText;

                // Устанавливаем текст письма
                message.setText(personalizedMessage);

                // Отправка письма
                Transport.send(message);

                // System.out.println("Письмо успешно отправлено на: " + recipient);
                i += 1;
                EmailSender.flagA = true; // Сообщение отправлено, для интерфейса
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
