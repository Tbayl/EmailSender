package org.example.course_project;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class Decryptor {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "gsugSDY0;497gdkgMvcwxzei";
    private static String decryptedText;

    public static String decryptAndStore(String inputFilePath, String ivFilePath) throws Exception {
        decryptedText = decryptFile(inputFilePath, ivFilePath);
        return decryptedText; // Возвращаем значение
    }

    public static String getDecryptedText() { // Геттер для decryptedText
        return decryptedText;
    }

    public static String decryptFile(String inputFilePath, String ivFilePath) throws Exception {
        byte[] iv = Base64.getDecoder().decode(readFile(ivFilePath));
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));


        try (CipherInputStream cis = new CipherInputStream(new FileInputStream(inputFilePath), cipher)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toString("UTF-8");
        }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
