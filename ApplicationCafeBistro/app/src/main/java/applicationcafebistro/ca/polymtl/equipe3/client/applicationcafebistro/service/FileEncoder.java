package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;


import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileEncoder {


    public static String encodeFileToBase64(File file) {
        String encodedFile = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            encodedFile = new String(Base64.encodeBase64(bytes));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedFile;
    }
}