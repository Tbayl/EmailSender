package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import org.example.course_project.Decryptor;
import org.example.course_project.EmailSender;
import org.example.course_project.message_to_recipients;
import org.example.course_project.recipients_of_the_mailing_list;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;




public class HelloController {


    private String messageText;
    private String from_sent;

    @FXML
    private Menu file_from_sent;

    @FXML
    private Menu file_from_text;

    @FXML
    void initialize(){
        file_from_sent.setOnAction(event -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setCurrentDirectory(new File(".")); // Текущая директория
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files", "txt")); // фильтр по формату файла

            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String recipient_addresses = selectedFile.getAbsolutePath();
                //System.out.println(recipient_addresses);
                if (recipient_addresses.substring(recipient_addresses.length() - 4).equals(".txt")) {
                    this.from_sent = recipients_of_the_mailing_list.choose_file(recipient_addresses); // Обработка файла с получателями
                }
                else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Выбран файл с недопустимым расширением(доступно только .txt)");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Получатели рассылки не выбраны");
                alert.showAndWait();
            }
        });

        file_from_text.setOnAction(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(".")); // Текущая директория
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files", "txt")); // фильтр по формату файла

            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String mailing_file = selectedFile.getAbsolutePath();
                System.out.println(mailing_file);
                if (mailing_file.substring(mailing_file.length() - 4).equals(".txt")) {
                    // Обработка файла с сообщением
                    this.messageText = message_to_recipients.choose_file(mailing_file); // сохраняем содержимое файла в переменную messageText
                }
                else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Выбран файл с недопустимым расширением(доступно только .txt)");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Текст для рассылки не выбран");
                alert.showAndWait();
            }
        });
    }

    @FXML
    private void handleSendMessage() {
        // Проверяем, что messageText не пусто перед отправкой
        if (messageText == null || messageText.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Сообщение пустое");
            alert.showAndWait();
            return;
        }

        // Логика отправки сообщения
        try {
            Decryptor.decryptAndStore("C:\\Users\\Вячеслав\\Desktop\\course_project\\course_project\\output.enc", "C:\\Users\\Вячеслав\\Desktop\\course_project\\course_project\\iv.txt");
            String decryptedPass = Decryptor.getDecryptedText();
            Decryptor.decryptAndStore("C:\\Users\\Вячеслав\\Desktop\\course_project\\course_project\\output_1.enc", "C:\\Users\\Вячеслав\\Desktop\\course_project\\course_project\\iv_1.txt");
            String decryptedLog = Decryptor.getDecryptedText();
            EmailSender.sendEmail(decryptedLog, decryptedPass, recipients_of_the_mailing_list.list_of_adress, "Важное сообщение", messageText);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        if (EmailSender.flagA) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setHeaderText(null);
            alert.setContentText("Сообщение отправлено!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Сообщение не отправлено");
            alert.showAndWait();
        }
    }
}
